package ru.otus.springframework.hw17.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.springframework.hw17.domain.Author;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
