package ru.otus.springframework.hw08.dao;

import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw08.domain.Genre;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface GenreRepository {
    List<Genre> getAll();
    void add(@Valid Genre genre);
}
