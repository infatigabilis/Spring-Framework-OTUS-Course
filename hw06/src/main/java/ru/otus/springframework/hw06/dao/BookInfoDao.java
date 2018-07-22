package ru.otus.springframework.hw06.dao;

import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw06.domain.BookInfo;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
public interface BookInfoDao {
    BookInfo getById(@Min(0) long id);
    List<BookInfo> getAll();
    void insert(@Valid BookInfo bookInfo);
    void addAuthor(@Min(0) long bookId, @Min(0) long authorId);
    void addGenre(@Min(0) long bookId, @Min(0) long genreId);
}
