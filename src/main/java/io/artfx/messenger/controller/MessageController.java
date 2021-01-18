package io.artfx.messenger.controller;

import io.artfx.messenger.entity.ChatMessage;
import io.artfx.messenger.model.AppUserDetailsModel;
import io.artfx.messenger.service.ChatMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/messages/api/messages")
public class MessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping
    public List<ChatMessage> getMessages(Authentication authentication,
            @RequestParam("senderUuid") String senderUuid,
            @RequestParam("recipientUuid") String recipientUuid) {
        AppUserDetailsModel userDetails = (AppUserDetailsModel) authentication.getPrincipal();
        return chatMessageService.findChatMessages(userDetails.getUuid(), senderUuid, recipientUuid);
    }

    @GetMapping("/{uuid}")
    public ChatMessage getMessage(Authentication authentication, @PathVariable String uuid) {
        AppUserDetailsModel userDetails = (AppUserDetailsModel) authentication.getPrincipal();
        return chatMessageService.findByUuid(userDetails.getUuid(), uuid);
    }
}
