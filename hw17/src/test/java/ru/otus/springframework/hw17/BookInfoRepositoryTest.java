package ru.otus.springframework.hw17;

import org.junit.Test;
import ru.otus.springframework.hw17.base.BaseRepositoryTest;
import ru.otus.springframework.hw17.domain.Author;
import ru.otus.springframework.hw17.domain.BookInfo;
import ru.otus.springframework.hw17.domain.Genre;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class BookInfoRepositoryTest extends BaseRepositoryTest {

    @Test
    public void whenFindById_thenReturnOne() {
        Author author1 = authorRepository.save(new Author(null, "Author1", null)).block();
        Author author2 = authorRepository.save(new Author(null, "Author2", "Surname")).block();

        Genre genre = genreRepository.save(new Genre(null, "Genre")).block();

        BookInfo expected = bookInfoRepository.persist(BookInfo.builder().title("Title").description("Desc").build()).block();
        bookInfoRepository.addAuthorById(expected.getId(), author1.getId()).block();
        bookInfoRepository.addAuthorById(expected.getId(), author2.getId()).block();
        bookInfoRepository.addGenreById(expected.getId(), genre.getId()).block();


        expected.setAuthors(Arrays.asList(author1, author2));
        expected.setGenres(Collections.singletonList(genre));

        final BookInfo actual = bookInfoRepository.getOne(expected.getId()).block();

        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getAuthors(), actual.getAuthors());
        assertEquals(expected.getGenres(), actual.getGenres());
    }

    @Test
    public void whenGetAll_thenReturnAll() {
        final BookInfo expected1 = bookInfoRepository.persist(new BookInfo(null, "Title1", "Desc1", Collections.emptyList(), Collections.emptyList())).block();
        final BookInfo expected2 = bookInfoRepository.persist(new BookInfo(null, "Title2", "Desc2", Collections.emptyList(), Collections.emptyList())).block();

        final List<BookInfo> actual = bookInfoRepository.getAll().toStream().collect(Collectors.toList());

        assertEquals(2, actual.size());
    }
}
