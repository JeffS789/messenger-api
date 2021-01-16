package io.artfx.messenger.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.artfx.messenger.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatRoomView {
    private String userUuid;
    private Profile profile;
    private Instant lastMessageTimestamp;
    private String lastMessage;
}
