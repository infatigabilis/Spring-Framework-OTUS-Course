package ru.otus.springframework.hw08.repository;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.springframework.hw08.domain.Author;
import ru.otus.springframework.hw08.repository.base.BaseRepositoryTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AuthorRepositoryTest extends BaseRepositoryTest {
    @Autowired private AuthorRepository repository;

    @After
    public void setUp() {
        transactionTemplate.execute(status -> {
            repository.getAll().forEach(o -> em.remove(o));
            return null;
        });
    }

    @Test
    public void whenFindById_thenReturnOne() {
        final Author expected = new Author(null, "Name", "Surname");
        repository.add(expected);

        final Author actual = repository.getById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void whenGetAll_thenReturnAll() {
        final Author expected1 = new Author(null, "Name", "Surname");
        repository.add(expected1);
        final Author expected2 = new Author(null, "Name2", "Surname2");
        repository.add(expected2);

        final List<Author> actual = repository.getAll();

        assertEquals(Arrays.asList(expected1, expected2), actual);
    }
}
