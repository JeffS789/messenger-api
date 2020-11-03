package io.artfx.messenger.service;


import io.artfx.messenger.entity.User;
import io.artfx.messenger.model.AppUserDetailsModel;
import io.artfx.messenger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found: " + userName));
        return user.map(AppUserDetailsModel::new).get();
    }
}
