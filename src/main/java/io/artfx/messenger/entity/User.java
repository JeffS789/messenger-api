package io.artfx.messenger.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.artfx.messenger.enums.RoleType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user", indexes = {@Index(name = "user_username_idx0", columnList = "username")})
public class User extends BaseEntity {
    private String username;
    @JsonIgnore
    private Boolean verified;
    @JsonIgnore
    private Boolean active;
    @JsonIgnore
    private String password;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_uuid", foreignKey = @ForeignKey(name = "fk_user_organization_uuid"))
    private Organization organization;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "uuid"))
    @Enumerated(EnumType.STRING)
    private List<RoleType> roles = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_uuid", foreignKey = @ForeignKey(name = "fk_user_profile_uuid"))
    private Profile profile;
}

