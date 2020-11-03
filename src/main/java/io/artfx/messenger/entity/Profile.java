package io.artfx.messenger.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include. NON_NULL)
public class Profile extends BaseEntity {
    private String firstName;
    private String lastName;
    private String company;
    private String avatar;
    private String displayName;
    private String phone;
}
