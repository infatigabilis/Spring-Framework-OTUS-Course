package ru.otus.springframework.hw06.dao.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.otus.springframework.hw06.dao.GenreDao;
import ru.otus.springframework.hw06.domain.Genre;

import java.util.HashMap;
import java.util.List;

@Service
public class GenreJdbcDao implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    public GenreJdbcDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from genres", (resultSet, i) -> new Genre(
                resultSet.getLong("id"),
                resultSet.getString("name")
        ));
    }

    @Override
    public void insert(Genre author) {
        jdbc.update("insert into genres (`name`) values (:name)", new HashMap<String, Object>() {{
            put("name", author.getName());
        }});
    }
}
