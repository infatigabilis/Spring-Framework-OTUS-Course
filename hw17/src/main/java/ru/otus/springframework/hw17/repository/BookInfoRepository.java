package ru.otus.springframework.hw17.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.springframework.hw17.persistence.BookInfoDocument;

@Repository
public interface BookInfoRepository extends ReactiveMongoRepository<BookInfoDocument, String>, BookInfoRepositoryCustom {
}
