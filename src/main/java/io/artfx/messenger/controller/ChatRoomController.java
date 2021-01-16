package io.artfx.messenger.controller;

import io.artfx.messenger.service.ChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/chatrooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/recipient/{recipientUuid}")
    public String getChatRoom(Authentication authentication, String recipientUuid) {
        return chatRoomService.getChatId(authentication.getName(), recipientUuid);
    }
}
