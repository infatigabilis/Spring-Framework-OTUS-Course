package ru.otus.springframework.hw08;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.springframework.hw08.domain.Author;
import ru.otus.springframework.hw08.domain.BookInfo;
import ru.otus.springframework.hw08.domain.Genre;
import ru.otus.springframework.hw08.repository.AuthorRepository;
import ru.otus.springframework.hw08.repository.BookInfoRepository;
import ru.otus.springframework.hw08.repository.GenreRepository;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

public class ValidationTest extends BaseTest {

    @Autowired private BookInfoRepository bookInfoRepository;
    @Autowired private AuthorRepository authorRepository;
    @Autowired private GenreRepository genreRepository;

    @Test(expected = ConstraintViolationException.class)
    public void addBook1() {
        bookInfoRepository.add(new BookInfo(0L, "Title", "Desc", Collections.emptySet(), Collections.emptySet(), Collections.emptyList()));
    }

    @Test(expected = ConstraintViolationException.class)
    public void addBook2() {
        bookInfoRepository.add(new BookInfo(null, "", "Desc", Collections.emptySet(), Collections.emptySet(), Collections.emptyList()));
    }

    @Test(expected = ConstraintViolationException.class)
    public void addAuthor1() {
        authorRepository.add(new Author(0L, "Name", "Surname"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void addAuthor2() {
        authorRepository.add(new Author(1L, "", "Surname"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void addGenre1() {
        genreRepository.add(new Genre(0L, "Genre"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void addGenre2() {
        genreRepository.add(new Genre(1L, ""));
    }
}
