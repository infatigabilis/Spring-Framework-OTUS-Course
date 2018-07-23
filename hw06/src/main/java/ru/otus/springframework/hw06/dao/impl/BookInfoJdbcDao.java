package ru.otus.springframework.hw06.dao.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.otus.springframework.hw06.dao.BookInfoDao;
import ru.otus.springframework.hw06.domain.Author;
import ru.otus.springframework.hw06.domain.BookInfo;
import ru.otus.springframework.hw06.domain.Genre;

import java.util.*;

@Service
public class BookInfoJdbcDao implements BookInfoDao {
    private static final BookInfo DUMMY_BOOK = new BookInfo();

    private final NamedParameterJdbcOperations jdbc;

    public BookInfoJdbcDao(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public BookInfo getById(long id) {
        final BookInfo book = new BookInfo();

        final String sql =
                "select " +
                        "b.id as b_id, " +
                        "b.title as b_title, " +
                        "b.description as b_description, " +
                        "a.id as a_id, " +
                        "a.name as a_name, " +
                        "a.surname as a_surname, " +
                        "g.id as g_id, " +
                        "g.name as g_name " +
                "from books b " +
                    "left join genre_book gb on gb.book_id=b.id " +
                    "left join genres g on gb.genre_id=g.id " +
                    "left join author_book ab on ab.book_id=b.id " +
                    "left join authors a on ab.author_id=a.id " +
                        "where b.id = :id";

        jdbc.query(sql, Collections.singletonMap("id", id), (resultSet, i) -> {
            book.setId(resultSet.getLong("b_id"));
            book.setTitle(resultSet.getString("b_title"));
            book.setDescription(resultSet.getString("b_description"));

            final Author newAuthor = new Author(
                    resultSet.getLong("a_id"),
                    resultSet.getString("a_name"),
                    resultSet.getString("a_surname")
            );
            if (resultSet.getLong("a_id") != 0 && !book.getAuthors().contains(newAuthor)) {
                book.getAuthors().add(newAuthor);
            }

            final Genre newGenre = new Genre(resultSet.getLong("g_id"), resultSet.getString("g_name"));
            if (resultSet.getLong("g_id") != 0 && !book.getGenres().contains(newGenre)) {
                book.getGenres().add(newGenre);
            }

            return DUMMY_BOOK;
        });

        return book;
    }

    @Override
    public List<BookInfo> getAll() {
        final Map<Long, BookInfo> books = new HashMap<>();

        final String sql =
                "select " +
                        "b.id as b_id, " +
                        "b.title as b_title, " +
                        "b.description as b_description, " +
                        "a.id as a_id, " +
                        "a.name as a_name, " +
                        "a.surname as a_surname, " +
                        "g.id as g_id, " +
                        "g.name as g_name " +
                "from books b " +
                    "left join genre_book gb on gb.book_id=b.id " +
                    "left join genres g on gb.genre_id=g.id " +
                    "left join author_book ab on ab.book_id=b.id " +
                    "left join authors a on ab.author_id=a.id";

        jdbc.query(sql, (resultSet, i) -> {
            final BookInfo book = books.getOrDefault(resultSet.getLong("id"), new BookInfo() {{
                setId(resultSet.getLong("b_id"));
                setTitle(resultSet.getString("b_title"));
                setDescription(resultSet.getString("b_description"));
            }});

            final Author newAuthor = new Author(
                    resultSet.getLong("a_id"),
                    resultSet.getString("a_name"),
                    resultSet.getString("a_surname")
            );
            if (resultSet.getLong("a_id") != 0 && !book.getAuthors().contains(newAuthor)) {
                book.getAuthors().add(newAuthor);
            }

            final Genre newGenre = new Genre(resultSet.getLong("g_id"), resultSet.getString("g_name"));
            if (resultSet.getLong("g_id") != 0 && !book.getGenres().contains(newGenre)) {
                book.getGenres().add(newGenre);
            }

            books.put(book.getId(), book);

            return DUMMY_BOOK;
        });

        return new ArrayList<>(books.values());
    }

    @Override
    public void insert(BookInfo bookInfo) {
        jdbc.update("insert into books (`title`, `description`) values (:name, :description)", new HashMap<String, Object>() {{
            put("name", bookInfo.getTitle());
            put("description", bookInfo.getDescription());
        }});
    }

    @Override
    public void addAuthor(long bookId, long authorId) {
        jdbc.update("insert into author_book (author_id, book_id) values (:authorId, :bookId)", new HashMap<String, Object>() {{
            put("authorId", authorId);
            put("bookId", bookId);
        }});
    }

    @Override
    public void addGenre(long bookId, long genreId) {
        jdbc.update("insert into genre_book (genre_id, book_id) values (:genreId, :bookId)", new HashMap<String, Object>() {{
            put("genreId", genreId);
            put("bookId", bookId);
        }});
    }
}
