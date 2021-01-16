package io.artfx.messenger.service;

import io.artfx.messenger.entity.ChatMessage;
import io.artfx.messenger.enums.MessageStatus;
import io.artfx.messenger.repository.ChatMessageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    @Transactional
    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        return chatMessageRepository.save(chatMessage);
    }

    @Transactional
    public List<ChatMessage> findChatMessages(String userUuid, String senderUuid, String recipientUuid) {
        if (!userUuid.equals(senderUuid) && !userUuid.equals(recipientUuid)) {
            throw new RuntimeException("Not authorization to retrieve messages");
        }
        String chatId = chatRoomService.getChatId(senderUuid, recipientUuid);
        List<ChatMessage> messages = chatMessageRepository.findByChatIdOrderByCreatedDateAsc(chatId);
        messages.forEach(message -> {
                if (userUuid.equals(message.getRecipientUuid())) {
                    message.setStatus(MessageStatus.DELIVERED);
                }
            }
        );
        return messages;
    }

    @Transactional
    public ChatMessage findByUuid(String userUuid, String uuid) {
        Optional<ChatMessage> chatMessage = chatMessageRepository.findByUuid(uuid, userUuid);
        chatMessage.orElseThrow(() -> new RuntimeException("Unable to retrieve message with uuid: " + uuid));
        ChatMessage message = chatMessage.get();
        message.setStatus(MessageStatus.DELIVERED);
        return message;
    }

    @Transactional(readOnly = true)
    public long countNewMessages(String senderId, String recipientId) {
        return chatMessageRepository.countBySenderUuidAndRecipientUuidAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }
}
