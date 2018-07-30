package ru.otus.springframework.hw08.repository;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.springframework.hw08.domain.Author;
import ru.otus.springframework.hw08.domain.BookInfo;
import ru.otus.springframework.hw08.domain.Genre;
import ru.otus.springframework.hw08.repository.base.BaseRepositoryTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BookInfoRepositoryTest extends BaseRepositoryTest {
    @Autowired private AuthorRepository authorRepository;
    @Autowired private GenreRepository genreRepository;
    @Autowired private BookInfoRepository repository;
    @Autowired private CommentRepository commentRepository;

    @After
    public void setUp() {
        transactionTemplate.execute(status -> {
            repository.getAll().forEach(o -> {
                o.getGenres().forEach(g -> em.remove(g));
                o.getAuthors().forEach(a -> em.remove(a));
                em.remove(o);
            });
            return null;
        });
    }

    @Test
    public void whenFindById_thenReturnOne() {
        Author author1 = new Author(null, "Author1", null);
        authorRepository.add(author1);
        Author author2 = new Author(null, "Author2", "Surname");
        authorRepository.add(author2);

        Genre genre = new Genre(null, "Genre");
        genreRepository.add(genre);


        final BookInfo expected = new BookInfo(
                null,
                "Title",
                "Desc",
                new LinkedHashSet<>(Arrays.asList(author1, author2)),
                Collections.singleton(genre),
                Collections.emptyList()
        );

        repository.add(expected);


        final BookInfo actual = repository.getById(expected.getId());

        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getAuthors(), actual.getAuthors());
        assertEquals(expected.getGenres(), actual.getGenres());
    }

    @Test
    public void whenGetAll_thenReturnAll() {
        final BookInfo expected1 = new BookInfo(null, "Title1", "Desc1", Collections.emptySet(), Collections.emptySet(), Collections.emptyList());
        repository.add(expected1);
        final BookInfo expected2 = new BookInfo(null, "Title2", "Desc2", Collections.emptySet(), Collections.emptySet(), Collections.emptyList());
        repository.add(expected2);

        final List<BookInfo> actual = repository.getAll();

        assertEquals(2, actual.size());
    }
}
