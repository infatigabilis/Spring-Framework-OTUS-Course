package ru.otus.springframework.hw19.repository;

import org.junit.After;
import org.junit.Test;
import ru.otus.springframework.hw19.domain.Author;
import ru.otus.springframework.hw19.domain.BookInfo;
import ru.otus.springframework.hw19.domain.Genre;
import ru.otus.springframework.hw19.repository.base.BaseRepositoryTest;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BookInfoRepositoryTest extends BaseRepositoryTest {

    @After
    public void tearDown() {
        deleteAll();
    }


    @Test
    public void whenFindById_thenReturnOne() {
        Author author1 = new Author(null, "Author1", null);
        authorRepository.save(author1);
        Author author2 = new Author(null, "Author2", "Surname");
        authorRepository.save(author2);

        Genre genre = new Genre(null, "Genre");
        genreRepository.save(genre);

        BookInfo book = BookInfo.builder().title("Title").description("Desc").build();


        bookInfoRepository.save(book);
        bookInfoRepository.addAuthorById(book.getId(), author1.getId());
        bookInfoRepository.addAuthorById(book.getId(), author2.getId());
        bookInfoRepository.addGenreById(book.getId(), genre.getId());


        final BookInfo actual = bookInfoRepository.getOne(book.getId());

        assertEquals(book.getTitle(), actual.getTitle());
        assertEquals(book.getDescription(), actual.getDescription());
        assertEquals(book.getAuthors(), actual.getAuthors());
        assertEquals(book.getGenres(), actual.getGenres());
    }

    @Test
    public void whenGetAll_thenReturnAll() {
        final BookInfo expected1 = new BookInfo(null, "Title1", "Desc1", Collections.emptySet(), Collections.emptySet(), Collections.emptyList());
        bookInfoRepository.save(expected1);
        final BookInfo expected2 = new BookInfo(null, "Title2", "Desc2", Collections.emptySet(), Collections.emptySet(), Collections.emptyList());
        bookInfoRepository.save(expected2);

        final List<BookInfo> actual = bookInfoRepository.findAll();

        assertEquals(2, actual.size());
    }
}
