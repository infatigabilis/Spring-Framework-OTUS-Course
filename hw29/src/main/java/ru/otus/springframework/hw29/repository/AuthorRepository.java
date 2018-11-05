package ru.otus.springframework.hw29.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.springframework.hw29.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
