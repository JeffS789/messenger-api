package io.artfx.messenger.controller;

import io.artfx.messenger.entity.ChatRoom;
import io.artfx.messenger.repository.ChatRoomRepository;
import io.artfx.messenger.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @GetMapping("/recipient/{recipientUuid}")
    public String getChatRoom(Authentication authentication, String recipientUuid) {
        return chatRoomService.getChatId(authentication.getName(), recipientUuid);
    }

//    @GetMapping
//    public List<ChatRoom> getChatRooms() {
//
//    }

}
