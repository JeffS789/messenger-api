package io.artfx.messenger.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.artfx.messenger.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "organization", indexes = {
        @Index(name = "organization_name_idx0", columnList = "name"),
        @Index(name = "organization_domain_idx0", columnList = "domain"),
        @Index(name = "organization_role_idx0", columnList = "role")
})
public class Organization extends BaseEntity {

    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String domain;
    private String address;
    private String phone;
    private String indTaxIdNumber;
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "organization")
    private List<User> users = new ArrayList<>();
}
