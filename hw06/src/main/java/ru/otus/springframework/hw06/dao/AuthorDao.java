package ru.otus.springframework.hw06.dao;

import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw06.domain.Author;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface AuthorDao {
    List<Author> getAll();
    void insert(@Valid Author author);
}
