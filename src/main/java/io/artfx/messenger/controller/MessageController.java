package io.artfx.messenger.controller;

import io.artfx.messenger.entity.ChatMessage;
import io.artfx.messenger.service.ChatMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class MessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/api/messages")
    public List<ChatMessage> getMessages(
            @RequestParam("senderUuid") String senderUuid,
            @RequestParam("recipientUuid") String recipientUuid) {
        return chatMessageService.findChatMessages(senderUuid, recipientUuid);
    }

}
