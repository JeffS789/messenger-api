package io.artfx.messenger.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user", indexes = {@Index(name = "user_username_idx0", columnList = "username")})
public class User extends BaseEntity {
    private String username;
    @JsonIgnore
    private boolean verified;
    @JsonIgnore
    private boolean active;
    @JsonIgnore
    private boolean superAdmin;
    @JsonIgnore
    private String roles;
    @JsonIgnore
    private String password;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_uuid", foreignKey = @ForeignKey(name = "fk_user_profile_uuid"))
    private Profile profile;
}

