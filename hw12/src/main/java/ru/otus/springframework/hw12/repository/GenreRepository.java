package ru.otus.springframework.hw12.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.springframework.hw12.domain.Genre;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {
}
