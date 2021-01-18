package io.artfx.messenger.service;

import io.artfx.messenger.entity.User;
import io.artfx.messenger.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User getUserByUuid(String uuid) {
        Optional<User> user = userRepository.findById(uuid);
        user.orElseThrow(() -> new RuntimeException("User uuid not found: " + uuid));
        User userPersistent = user.get();
        return userPersistent;
    }
}
