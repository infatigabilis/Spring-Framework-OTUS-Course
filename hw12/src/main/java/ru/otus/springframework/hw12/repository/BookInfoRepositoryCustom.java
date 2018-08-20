package ru.otus.springframework.hw12.repository;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
public interface BookInfoRepositoryCustom {
    void addAuthorById(@NotBlank String bookId, @NotBlank String authorId);
    void addGenreById(@NotBlank String bookId, @NotBlank String genreId);
}
