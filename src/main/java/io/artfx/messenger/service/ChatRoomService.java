package io.artfx.messenger.service;

import io.artfx.messenger.entity.ChatMessage;
import io.artfx.messenger.entity.ChatRoom;
import io.artfx.messenger.entity.User;
import io.artfx.messenger.model.ChatRoomView;
import io.artfx.messenger.repository.ChatMessageRepository;
import io.artfx.messenger.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<ChatRoomView> findChatRooms(String userUuid) {
        // First chat rooms with user being equal to either sender or recipient
        List<ChatRoomView> chatRoomViews = new ArrayList<>();
        List<ChatRoom> chatRooms = chatRoomRepository.findAllVisibleChatRooms(userUuid);
        chatRooms.forEach(chatRoom -> {
            User user = null;
            if (!chatRoom.getRecipientUuid().equals(userUuid)) {
                user = userService.getUserByUuid(chatRoom.getRecipientUuid());
            }
            if (!chatRoom.getSenderUuid().equals(userUuid)) {
                user = userService.getUserByUuid(chatRoom.getSenderUuid());
            }
            if (user == null) {
                throw new RuntimeException("Unable to find user form either Sender or Recipient");
            }
            Optional<ChatMessage> chatMessage = chatMessageRepository.findFirstBySenderUuidAndRecipientUuidOrderByTimestampAsc(user.getUuid(), userUuid);
            ChatMessage message = null;
            if (chatMessage.isPresent()) {
                message = chatMessage.get();
            }
            chatRoomViews.add(ChatRoomView.builder()
                    .userUuid(user.getUuid())
                    .lastMessage(message != null ? message.getContent(): null)
                    .lastMessageTimestamp(message != null ? message.getTimestamp(): null)
                    .profile(user.getProfile())
                    .build());
        });
        return chatRoomViews;
    }

    @Transactional
    public String getChatId(String senderUuid, String recipientUuid) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findBySenderUuidAndRecipientUuid(senderUuid, recipientUuid);
        if(chatRoom.isPresent()) {
            return chatRoom.get().getChatId();
        }
        String chatId = senderUuid + "_" + recipientUuid;
        ChatRoom senderRecipient = ChatRoom
                .builder()
                .chatId(chatId)
                .senderUuid(senderUuid)
                .recipientUuid(recipientUuid)
                .build();

        ChatRoom recipientSender = ChatRoom
                .builder()
                .chatId(chatId)
                .senderUuid(recipientUuid)
                .recipientUuid(senderUuid)
                .build();
        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);

        return senderRecipient.getChatId();
    }
}
