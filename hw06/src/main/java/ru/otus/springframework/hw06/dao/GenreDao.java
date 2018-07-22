package ru.otus.springframework.hw06.dao;

import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw06.domain.Genre;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface GenreDao {
    List<Genre> getAll();
    void insert(@Valid Genre author);
}
