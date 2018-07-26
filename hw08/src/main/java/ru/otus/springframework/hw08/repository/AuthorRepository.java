package ru.otus.springframework.hw08.dao;

import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw08.domain.Author;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface AuthorRepository {
    List<Author> getAll();
    void add(@Valid Author author);
}
