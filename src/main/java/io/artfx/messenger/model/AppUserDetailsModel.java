package io.artfx.messenger.model;

import io.artfx.messenger.entity.User;
import io.artfx.messenger.enums.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AppUserDetailsModel implements UserDetails {

    private String username;
    private String password;
    private String uuid;
    private String organizationName;
    private String organizationUuid;
    private String firstName;
    private String lastName;
    private String displayName;
    private Boolean verified;
    private Boolean active;
    private List<GrantedAuthority> authorities;

    public AppUserDetailsModel(User user) {
        this.uuid = user.getUuid();
        this.username = user.getUsername();
        this.password = user.getPassword();
        if (!ObjectUtils.isEmpty(user.getOrganization())) {
            this.organizationName = user.getOrganization().getName();
            this.organizationUuid = user.getOrganization().getUuid();
        }
        this.firstName = user.getProfile().getFirstName();
        this.lastName = user.getProfile().getLastName();
        this.displayName = user.getProfile().getDisplayName();
        this.active = user.getActive();
        this.verified = user.getVerified();
        this.authorities = new ArrayList<>();
        for (RoleType role: user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
