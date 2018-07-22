package ru.otus.springframework.hw06.dao.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.otus.springframework.hw06.dao.AuthorDao;
import ru.otus.springframework.hw06.domain.Author;

import java.util.HashMap;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class AuthorJdbcDao implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    public AuthorJdbcDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select * from authors", (resultSet, i) -> new Author(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("surname")
        ));
    }

    @Override
    public void insert(Author author) {
        if (!isEmpty(author.getSurname())) {
            jdbc.update("insert into authors (`name`, `surname`) values (:name, :desc)", new HashMap<String, Object>() {{
                put("name", author.getName());
                put("surname", author.getSurname());
            }});
        } else {
            jdbc.update("insert into authors (`name`) values (:name)", new HashMap<String, Object>() {{
                put("name", author.getName());
            }});
        }
    }
}
