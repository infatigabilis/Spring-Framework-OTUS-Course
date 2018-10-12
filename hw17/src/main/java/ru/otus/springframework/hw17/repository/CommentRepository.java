package ru.otus.springframework.hw17.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.otus.springframework.hw17.persistence.CommentDocument;

import javax.validation.constraints.NotBlank;

@Repository
public interface CommentRepository extends ReactiveMongoRepository<CommentDocument, String>, CommentRepositoryCustom {
    Flux<CommentDocument> findByBookInfoId(@NotBlank String bookId);
}
