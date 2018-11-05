package ru.otus.springframework.hw29.repository;

import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw29.domain.BookInfo;
import ru.otus.springframework.hw29.base.ValidationGroup;

import javax.validation.constraints.Min;
import javax.validation.groups.Default;

@Validated
public interface BookInfoRepositoryCustom {
    void addAuthorById(@Min(1) long bookId, @Min(1) long authorId);
    void addGenreById(@Min(1) long bookId, @Min(1) long genreId);
    void deleteAuthorById(@Min(1) long bookId, @Min(1) long authorId);
    void deleteGenreById(@Min(1) long bookId, @Min(1) long genreId);
    void merge(@Validated({Default.class, ValidationGroup.Update.class}) BookInfo bookInfo);
}
