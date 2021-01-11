package io.artfx.messenger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
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
