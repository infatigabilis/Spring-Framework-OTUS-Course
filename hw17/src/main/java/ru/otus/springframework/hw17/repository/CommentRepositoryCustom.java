package ru.otus.springframework.hw17.repository;

import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.springframework.hw17.domain.Comment;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Validated
public interface CommentRepositoryCustom {
    Mono<Comment> addWithBookById(@Valid Comment comment, @NotBlank String bookId);
    Flux<Comment> getByBookInfoId(@NotBlank String bookId);
}
