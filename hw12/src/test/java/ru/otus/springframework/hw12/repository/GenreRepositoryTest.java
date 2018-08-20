package ru.otus.springframework.hw12.repository;

import org.junit.After;
import org.junit.Test;
import ru.otus.springframework.hw12.domain.Genre;
import ru.otus.springframework.hw12.repository.base.BaseRepositoryTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GenreRepositoryTest extends BaseRepositoryTest {

    @After
    public void tearDown() {
        deleteAll();
    }


    @Test
    public void whenFindById_thenReturnOne() {
        final Genre expected = new Genre(null, "Name");
        genreRepository.save(expected);

        final Genre actual = genreRepository.findById(expected.getId()).get();

        assertEquals(expected, actual);
    }

    @Test
    public void whenGetAll_thenReturnAll() {
        final Genre expected1 = new Genre(null, "Name");
        genreRepository.save(expected1);
        final Genre expected2 = new Genre(null, "Name2");
        genreRepository.save(expected2);

        final List<Genre> actual = genreRepository.findAll();

        assertEquals(Arrays.asList(expected1, expected2), actual);
    }
}
