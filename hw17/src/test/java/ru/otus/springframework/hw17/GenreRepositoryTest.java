package ru.otus.springframework.hw17;

import org.junit.Test;
import ru.otus.springframework.hw17.base.BaseRepositoryTest;
import ru.otus.springframework.hw17.domain.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class GenreRepositoryTest extends BaseRepositoryTest {

    @Test
    public void whenFindById_thenReturnOne() {
        final Genre expected = genreRepository.save(new Genre(null, "Name")).block();

        final Genre actual = genreRepository.findById(expected.getId()).block();

        assertEquals(expected, actual);
    }

    @Test
    public void whenGetAll_thenReturnAll() {
        final Genre expected1 = genreRepository.save(new Genre(null, "Name")).block();
        final Genre expected2 = genreRepository.save(new Genre(null, "Name2")).block();

        final List<Genre> actual = genreRepository.findAll().toStream().collect(Collectors.toList());

        assertEquals(Arrays.asList(expected1, expected2), actual);
    }
}
