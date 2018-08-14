package ru.otus.springframework.hw12.repository;

import org.junit.After;
import org.junit.Test;
import ru.otus.springframework.hw12.domain.Author;
import ru.otus.springframework.hw12.domain.BookInfo;
import ru.otus.springframework.hw12.domain.Genre;
import ru.otus.springframework.hw12.repository.base.BaseRepositoryTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
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

        BookInfo expected = BookInfo.builder().title("Title").description("Desc").build();


        bookInfoRepository.save(expected);
        bookInfoRepository.addAuthorById(expected.getId(), author1.getId());
        bookInfoRepository.addAuthorById(expected.getId(), author2.getId());
        bookInfoRepository.addGenreById(expected.getId(), genre.getId());


        expected.setAuthors(new HashSet<>(Arrays.asList(author1, author2)));
        expected.setGenres(Collections.singleton(genre));

        final BookInfo actual = bookInfoRepository.findById(expected.getId()).get();

        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getAuthors(), actual.getAuthors());
        assertEquals(expected.getGenres(), actual.getGenres());
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
