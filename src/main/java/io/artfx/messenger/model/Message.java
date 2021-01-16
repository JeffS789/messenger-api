package io.artfx.messenger.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class Message {
    private String senderUuid;
    private String recipientUuid;
    private String content;
    private Instant timestamp;
}
