package ru.otus.springframework.hw19.repository;

import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw19.base.ValidationGroup.Update;
import ru.otus.springframework.hw19.domain.BookInfo;

import javax.validation.constraints.Min;
import javax.validation.groups.Default;

@Validated
public interface BookInfoRepositoryCustom {
    void addAuthorById(@Min(1) long bookId, @Min(1) long authorId);
    void addGenreById(@Min(1) long bookId, @Min(1) long genreId);
    void deleteAuthorById(@Min(1) long bookId, @Min(1) long authorId);
    void deleteGenreById(@Min(1) long bookId, @Min(1) long genreId);
    void merge(@Validated({Default.class, Update.class}) BookInfo bookInfo);
}
