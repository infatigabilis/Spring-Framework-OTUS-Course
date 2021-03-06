package ru.otus.springframework.hw21.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.springframework.hw21.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
}
