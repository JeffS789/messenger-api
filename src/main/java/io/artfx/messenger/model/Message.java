package io.artfx.messenger.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Message {
    private String senderUuid;
    private String recipientUuid;
    private String content;
    private Date timestamp;
}
