package io.artfx.messenger.repository;

import io.artfx.messenger.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    Optional<ChatRoom> findBySenderUuidAndRecipientUuid(String senderUuid, String recipientUuid);
    @Query("select c from ChatRoom c where (c.senderUuid = :userUuid or c.recipientUuid = :userUuid) and c.visible = true")
    List<ChatRoom> findAllVisibleChatRooms(String userUuid);
}
