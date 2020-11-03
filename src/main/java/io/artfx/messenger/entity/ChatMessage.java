package io.artfx.messenger.entity;

import io.artfx.messenger.enums.MessageStatus;
import io.artfx.messenger.model.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(indexes = {
        @Index(name = "chat_message_sender_uuid_idx0", columnList = "sender_uuid"),
        @Index(name = "chat_message_recipient_uuid_idx0", columnList = "recipient_uuid")
})
public class ChatMessage extends BaseEntity {

    public ChatMessage(Message message) {
        this.senderUuid = message.getSenderUuid();
        this.recipientUuid = message.getRecipientUuid();
        this.content = message.getContent();
        this.timestamp = message.getTimestamp();
    }

    @Column(name = "sender_uuid")
    private String senderUuid;
    @Column(name = "recipient_uuid")
    private String recipientUuid;
    private String chatId;
    private String content;
    private Date timestamp;
    private MessageStatus status;
}
