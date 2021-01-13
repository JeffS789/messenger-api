package io.artfx.messenger.controller;

import io.artfx.messenger.entity.ChatMessage;
import io.artfx.messenger.entity.ChatNotification;
import io.artfx.messenger.model.Message;
import io.artfx.messenger.service.ChatMessageService;
import io.artfx.messenger.service.ChatRoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@AllArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    @MessageMapping("/chat")
    public void onMessage(@Payload Message message) {
        ChatMessage chatMessage = new ChatMessage(message);
        String chatId = chatRoomService.getChatId(chatMessage.getSenderUuid(), chatMessage.getRecipientUuid());
        chatMessage.setChatId(chatId);
        ChatMessage saved = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientUuid(),"/queue/messages", new ChatNotification(saved.getUuid(), saved.getChatId(), saved.getSenderUuid()));
    }

}
