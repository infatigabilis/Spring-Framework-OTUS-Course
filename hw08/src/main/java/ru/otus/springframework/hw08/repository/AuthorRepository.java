package ru.otus.springframework.hw08.repository;

import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw08.domain.Author;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
public interface AuthorRepository {
    List<Author> getAll();
    Author getById(@Min(0) long id);
    void add(@Valid Author author);
}
