package ru.otus.springframework.hw15.repository;

import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw15.domain.BookInfo;

import javax.validation.constraints.Min;
import javax.validation.groups.Default;

import ru.otus.springframework.hw15.base.ValidationGroup.Update;

@Validated
public interface BookInfoRepositoryCustom {
    void addAuthorById(@Min(1) long bookId, @Min(1) long authorId);
    void addGenreById(@Min(1) long bookId, @Min(1) long genreId);
    void deleteAuthorById(@Min(1) long bookId, @Min(1) long authorId);
    void deleteGenreById(@Min(1) long bookId, @Min(1) long genreId);
    void merge(@Validated({Default.class, Update.class}) BookInfo bookInfo);
}
