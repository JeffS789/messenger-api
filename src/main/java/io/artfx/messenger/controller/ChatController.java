package io.artfx.messenger.controller;

import io.artfx.messenger.entity.ChatMessage;
import io.artfx.messenger.entity.ChatNotification;
import io.artfx.messenger.model.Message;
import io.artfx.messenger.service.ChatMessageService;
import io.artfx.messenger.service.ChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Slf4j
@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private ChatRoomService chatRoomService;

    @MessageMapping("/chat")
    public void processMessage(@Payload Message message) {
        ChatMessage chatMessage = new ChatMessage(message);
        String chatId = chatRoomService.getChatId(chatMessage.getSenderUuid(), chatMessage.getRecipientUuid());
        chatMessage.setChatId(chatId);
        ChatMessage saved = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientUuid(),"/queue/messages", new ChatNotification(saved.getUuid(), saved.getChatId(), saved.getSenderUuid()));
    }

}
