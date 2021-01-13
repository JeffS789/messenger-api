package io.artfx.messenger.service;

import io.artfx.messenger.entity.ChatRoom;
import io.artfx.messenger.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

//    @Transactional(readOnly = true)
//    public List<ChatRoom> getChatRooms() {
//
//    }

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
