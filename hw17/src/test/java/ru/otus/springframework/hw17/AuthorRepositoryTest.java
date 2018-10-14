package ru.otus.springframework.hw17;

import org.junit.Test;
import ru.otus.springframework.hw17.base.BaseRepositoryTest;
import ru.otus.springframework.hw17.domain.Author;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class AuthorRepositoryTest extends BaseRepositoryTest {

    @Test
    public void whenFindById_thenReturnOne() {
        Author expected = authorRepository.save(new Author(null, "Name", "Surname")).block();

        final Author actual = authorRepository.findById(expected.getId()).block();

        assertEquals(expected, actual);
    }

    @Test
    public void whenGetAll_thenReturnAll() {
        Author expected1 = authorRepository.save(new Author(null, "Name", "Surname")).block();
        Author expected2 = authorRepository.save(new Author(null, "Name2", "Surname2")).block();

        final List<Author> actual = authorRepository.findAll().toStream().collect(Collectors.toList());

        assertEquals(Arrays.asList(expected1, expected2), actual);
    }
}
