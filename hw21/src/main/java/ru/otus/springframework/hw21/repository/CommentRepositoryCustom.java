package ru.otus.springframework.hw21.repository;

import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw21.domain.Comment;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
public interface CommentRepositoryCustom {
    void addWithBookById(@Valid Comment comment, @Min(1) long bookId);
}
