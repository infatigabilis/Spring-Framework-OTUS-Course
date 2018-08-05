package ru.otus.springframework.hw10;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.springframework.hw10.domain.Author;
import ru.otus.springframework.hw10.domain.BookInfo;
import ru.otus.springframework.hw10.domain.Genre;
import ru.otus.springframework.hw10.repository.AuthorRepository;
import ru.otus.springframework.hw10.repository.BookInfoRepository;
import ru.otus.springframework.hw10.repository.GenreRepository;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

public class ValidationTest extends BaseTest {

    @Autowired private BookInfoRepository bookInfoRepository;
    @Autowired private AuthorRepository authorRepository;
    @Autowired private GenreRepository genreRepository;

    @Test(expected = ConstraintViolationException.class)
    public void addBook1() {
        bookInfoRepository.save(new BookInfo(0L, "Title", "Desc", Collections.emptySet(), Collections.emptySet(), Collections.emptyList()));
    }

    @Test(expected = ConstraintViolationException.class)
    public void addBook2() {
        bookInfoRepository.save(new BookInfo(null, "", "Desc", Collections.emptySet(), Collections.emptySet(), Collections.emptyList()));
    }

    @Test(expected = ConstraintViolationException.class)
    public void addAuthor1() {
        authorRepository.save(new Author(0L, "Name", "Surname"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void addAuthor2() {
        authorRepository.save(new Author(1L, "", "Surname"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void addGenre1() {
        genreRepository.save(new Genre(0L, "Genre"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void addGenre2() {
        genreRepository.save(new Genre(1L, ""));
    }
}
