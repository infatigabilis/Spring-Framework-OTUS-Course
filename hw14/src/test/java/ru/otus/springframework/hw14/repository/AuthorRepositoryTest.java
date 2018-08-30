package ru.otus.springframework.hw14.repository;

import org.junit.After;
import org.junit.Test;
import ru.otus.springframework.hw14.domain.Author;
import ru.otus.springframework.hw14.repository.base.BaseRepositoryTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AuthorRepositoryTest extends BaseRepositoryTest {

    @After
    public void tearDown() {
        deleteAll();
    }

    @Test
    public void whenFindById_thenReturnOne() {
        final Author expected = new Author(null, "Name", "Surname");
        authorRepository.save(expected);

        final Author actual = authorRepository.findById(expected.getId()).get();

        assertEquals(expected, actual);
    }

    @Test
    public void whenGetAll_thenReturnAll() {
        final Author expected1 = new Author(null, "Name", "Surname");
        authorRepository.save(expected1);
        final Author expected2 = new Author(null, "Name2", "Surname2");
        authorRepository.save(expected2);

        final List<Author> actual = authorRepository.findAll();

        assertEquals(Arrays.asList(expected1, expected2), actual);
    }
}
