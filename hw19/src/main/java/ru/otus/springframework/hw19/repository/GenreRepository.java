package ru.otus.springframework.hw19.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.springframework.hw19.domain.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
