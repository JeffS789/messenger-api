package io.artfx.messenger.repository;

import io.artfx.messenger.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    Optional<ChatRoom> findBySenderUuidAndRecipientUuid(String senderUuid, String recipientUuid);
}
