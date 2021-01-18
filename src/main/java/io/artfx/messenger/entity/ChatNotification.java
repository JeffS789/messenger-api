package io.artfx.messenger.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotification {
    private String messageUuid;
    private String chatId;
    private String senderUuid;
    private String contentPreview;
}
