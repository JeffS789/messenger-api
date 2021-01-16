package io.artfx.messenger.repository;

import io.artfx.messenger.entity.User;
import io.artfx.messenger.enums.MessageStatus;
import io.artfx.messenger.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
    long countBySenderUuidAndRecipientUuidAndStatus(String senderUuid, String recipientUuid, MessageStatus status);
    List<ChatMessage> findByChatIdOrderByCreatedDateAsc(String chatId);
    ChatMessage findFirstBySenderUuidAndRecipientUuidOrderByTimestampAsc(User sender, User recipient);
    @Query("select m from ChatMessage m where m.uuid = :uuid and (m.senderUuid = :userUuid or m.recipientUuid = :userUuid)")
    Optional<ChatMessage> findByUuid(String uuid, String userUuid);
    // ChatMessage findFirstBySenderAndRecipientOrderByTimestampAsc(User recipient, User sender);

}
