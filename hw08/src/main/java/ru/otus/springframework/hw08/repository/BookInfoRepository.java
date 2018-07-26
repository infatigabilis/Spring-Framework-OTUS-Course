package ru.otus.springframework.hw08.dao;

import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw08.domain.BookInfo;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
public interface BookInfoRepository {
    BookInfo getById(@Min(0) long id);
    List<BookInfo> getAll();
    void add(@Valid BookInfo bookInfo);
    void addAuthor(@Min(0) long bookId, @Min(0) long authorId);
    void addGenre(@Min(0) long bookId, @Min(0) long genreId);
}
