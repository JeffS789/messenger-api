package io.artfx.messenger.controller;

import io.artfx.messenger.model.AppUserDetailsModel;
import io.artfx.messenger.model.ChatRoomView;
import io.artfx.messenger.service.ChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/messages/api/chatrooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping
    public List<ChatRoomView> getChatRooms(Authentication authentication) {
        AppUserDetailsModel principle = (AppUserDetailsModel) authentication.getPrincipal();
        return chatRoomService.findChatRooms(principle.getUuid());
    }

    @GetMapping("/chatroom")
    public ChatRoomView getChatRoom(Authentication authentication,
                                    @RequestParam("senderUuid") String senderUuid,
                                    @RequestParam("recipientUuid") String recipientUuid) {
        AppUserDetailsModel principle = (AppUserDetailsModel) authentication.getPrincipal();
        return chatRoomService.getChatRoom(principle.getUuid(), senderUuid, recipientUuid);
    }
}
