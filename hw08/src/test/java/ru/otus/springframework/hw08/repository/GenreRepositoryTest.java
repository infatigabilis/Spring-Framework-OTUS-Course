package ru.otus.springframework.hw08.repository;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.springframework.hw08.domain.Genre;
import ru.otus.springframework.hw08.repository.base.BaseRepositoryTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GenreRepositoryTest extends BaseRepositoryTest {
    @Autowired private GenreRepository repository;

    @After
    public void setUp() {
        transactionTemplate.execute(status -> {
            repository.getAll().forEach(o -> em.remove(o));
            return null;
        });
    }

    @Test
    public void whenFindById_thenReturnOne() {
        final Genre expected = new Genre(null, "Name");
        repository.add(expected);

        final Genre actual = repository.getById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void whenGetAll_thenReturnAll() {
        final Genre expected1 = new Genre(null, "Name");
        repository.add(expected1);
        final Genre expected2 = new Genre(null, "Name2");
        repository.add(expected2);

        final List<Genre> actual = repository.getAll();

        assertEquals(Arrays.asList(expected1, expected2), actual);
    }
}
