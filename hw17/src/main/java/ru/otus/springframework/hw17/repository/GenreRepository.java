package ru.otus.springframework.hw17.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.springframework.hw17.domain.Genre;

@Repository
public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
