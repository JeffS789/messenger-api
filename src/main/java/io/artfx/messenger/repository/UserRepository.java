package io.artfx.messenger.repository;

import io.artfx.messenger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    List<User> findByProfileDisplayNameContainsOrProfileFirstNameContainsOrProfileLastNameContains(String displayName, String firstName, String lastName);
}
