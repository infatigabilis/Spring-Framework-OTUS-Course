package ru.otus.springframework.hw10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.springframework.hw10.domain.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
