package ru.otus.springframework.hw10.repository;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@Validated
public interface BookInfoRepositoryCustom {
    void addAuthorById(@Min(1) long bookId, @Min(1) long authorId);
    void addGenreById(@Min(1) long bookId, @Min(1) long genreId);
}
