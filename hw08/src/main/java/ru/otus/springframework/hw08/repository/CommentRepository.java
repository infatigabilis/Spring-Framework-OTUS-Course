package ru.otus.springframework.hw08.repository;

import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw08.domain.Comment;

import javax.validation.Valid;

@Validated
public interface CommentRepository {
    void add(@Valid Comment comment, long bookId);
}
