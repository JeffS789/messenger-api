package io.artfx.messenger.service;

import io.artfx.messenger.model.AppUserDetailsModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final JwtService jwtService;

    @Override
    public AppUserDetailsModel loadUserByUsername(String token) throws UsernameNotFoundException {
        String displayName = jwtService.getClaim(token, "displayName");
        log.info("Display Name: {}", displayName);
        return AppUserDetailsModel.builder()
                .uuid(jwtService.getClaim(token, "uuid"))
                .username(jwtService.getClaim(token, "username"))
                .firstName(jwtService.getClaim(token, "firstName"))
                .lastName(jwtService.getClaim(token, "lastName"))
                .displayName(jwtService.getClaim(token, "displayName"))
                .active(Boolean.parseBoolean(jwtService.getClaim(token, "active")))
                .verified(Boolean.parseBoolean(jwtService.getClaim(token, "verified")))
                .authorities(jwtService.getRoles(token))
                .build();
    }
}
