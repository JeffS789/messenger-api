package io.artfx.messenger.service;

import com.mysql.cj.util.StringUtils;
import io.artfx.messenger.entity.User;
import io.artfx.messenger.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> searchUsers(String query) {
        if (StringUtils.isNullOrEmpty(query)) {
            return List.of();
        }
        return userRepository.findByProfileDisplayNameContainsOrProfileFirstNameContainsOrProfileLastNameContains(query,query,query);
    }

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new RuntimeException("User not found: " + username));
        User userPersistent = user.get();
        return userPersistent;
    }

    @Transactional
    public User getUserByUuid(String uuid) {
        Optional<User> user = userRepository.findById(uuid);
        user.orElseThrow(() -> new RuntimeException("User uuid not found: " + uuid));
        User userPersistent = user.get();
        return userPersistent;
    }
}
