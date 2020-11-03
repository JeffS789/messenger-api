package io.artfx.messenger.repository;

import io.artfx.messenger.entity.User;
import io.artfx.messenger.enums.MessageStatus;
import io.artfx.messenger.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {

    long countBySenderUuidAndRecipientUuidAndStatus(String senderUuid, String recipientUuid, MessageStatus status);

    List<ChatMessage> findByChatIdOrderByCreatedDateAsc(String chatId);

    ChatMessage findFirstBySenderUuidAndRecipientUuidOrderByTimestampAsc(User sender, User recipient);
    // ChatMessage findFirstBySenderAndRecipientOrderByTimestampAsc(User recipient, User sender);

}
