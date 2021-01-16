package io.artfx.messenger.entity;

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
        @Index(name = "chat_room_sender_uuid_idx0", columnList = "sender_uuid"),
        @Index(name = "chat_room_recipient_uuid_idx0", columnList = "recipient_uuid")
})
public class ChatRoom extends BaseEntity {
    private String chatId;
    @Column(name = "sender_uuid")
    private String senderUuid;
    @Column(name = "recipient_uuid")
    private String recipientUuid;
    private boolean visible;
}
