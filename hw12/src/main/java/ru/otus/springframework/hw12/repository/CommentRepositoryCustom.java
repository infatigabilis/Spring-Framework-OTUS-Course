package ru.otus.springframework.hw12.repository;

import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw12.domain.Comment;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Validated
public interface CommentRepositoryCustom {
    void addWithBookById(@Valid Comment comment, @NotBlank String bookId);
}
