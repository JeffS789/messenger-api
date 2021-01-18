package io.artfx.messenger.service;

import io.artfx.messenger.entity.ChatMessage;
import io.artfx.messenger.entity.ChatRoom;
import io.artfx.messenger.entity.User;
import io.artfx.messenger.enums.MessageStatus;
import io.artfx.messenger.model.ChatRoomView;
import io.artfx.messenger.repository.ChatMessageRepository;
import io.artfx.messenger.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public ChatRoomView getChatRoom(String userUuid, String senderUuid, String recipientUuid) {
        if (!userUuid.equals(senderUuid) && !userUuid.equals(recipientUuid)) {
            throw new RuntimeException("Not authorized to fetch chat room");
        }
        String chatId = getChatId(senderUuid, recipientUuid);
        return buildChatRoom(chatId, userUuid, senderUuid, recipientUuid);
    }

    @Transactional(readOnly = true)
    public List<ChatRoomView> findChatRooms(String userUuid) {
        List<ChatRoomView> chatRoomViews = new ArrayList<>();
        Map<String, ChatRoom> chatRoomMap = new HashMap<>();
        chatRoomRepository.findAllVisibleChatRooms(userUuid)
                .forEach(chatRoom ->
                    chatRoomMap.put(chatRoom.getChatId(), chatRoom)
                );
        chatRoomMap.forEach((chatId, chatRoom) -> {
            ChatRoomView chatRoomView = buildChatRoom(chatId, userUuid, chatRoom.getSenderUuid(), chatRoom.getRecipientUuid());
            chatRoomViews.add(chatRoomView);
        });
        return chatRoomViews.stream().sorted(Comparator.comparing(ChatRoomView::getLastMessageTimestamp).reversed()).collect(Collectors.toList());
    }

    @Transactional
    public String getChatId(String senderUuid, String recipientUuid) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findBySenderUuidAndRecipientUuid(senderUuid, recipientUuid);
        if(chatRoom.isPresent()) {
            return chatRoom.get().getChatId();
        }
        String id = senderUuid + "_" + recipientUuid;
        String chatId = UUID.nameUUIDFromBytes(id.getBytes()).toString();
        ChatRoom senderRecipient = ChatRoom
                .builder()
                .chatId(chatId)
                .senderUuid(senderUuid)
                .recipientUuid(recipientUuid)
                .visible(true)
                .build();

        ChatRoom recipientSender = ChatRoom
                .builder()
                .chatId(chatId)
                .senderUuid(recipientUuid)
                .recipientUuid(senderUuid)
                .visible(true)
                .build();
        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);

        return senderRecipient.getChatId();
    }

    private ChatRoomView buildChatRoom(String chatId, String userUuid, String senderUuid, String recipientUuid) {
        User user = userService.getUserByUuid(userUuid.equals(senderUuid) ? recipientUuid : senderUuid);
        ChatMessage chatMessage = chatMessageRepository.findFirstByChatIdOrderByTimestampDesc(chatId);
        log.info("Fetching ChatRoom for chatId: {}", chatId);
        return ChatRoomView.builder()
                .chatId(chatId)
                .userUuid(user.getUuid())
                .lastMessage((userUuid.equals(chatMessage.getSenderUuid()) ? "You: " : "") + chatMessage.getContent())
                .lastMessageTimestamp(chatMessage.getTimestamp())
                .profile(user.getProfile())
                // TODO - Flawed because it doesnt look up all of the messages
                .isNewMessage((MessageStatus.RECEIVED).equals(chatMessage.getStatus()) && user.getUuid().equals(chatMessage.getSenderUuid()))
                .build();
    }
}
