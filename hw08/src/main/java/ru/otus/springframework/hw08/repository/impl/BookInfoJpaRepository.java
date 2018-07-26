package ru.otus.springframework.hw08.dao.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.otus.springframework.hw08.dao.BookInfoRepository;
import ru.otus.springframework.hw08.domain.Author;
import ru.otus.springframework.hw08.domain.BookInfo;
import ru.otus.springframework.hw08.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class BookInfoJpaRepository implements BookInfoRepository {
    private @PersistenceContext EntityManager em;

    @Override
    public BookInfo getById(long id) {
        return em.find(BookInfo.class, id);
    }

    @Override
    public List<BookInfo> getAll() {
        return em.createQuery("from BookInfo", BookInfo.class).getResultList();
    }

    @Override
    public void add(BookInfo bookInfo) {
        em.persist(bookInfo);
    }

    @Override
    public void addAuthor(long bookId, long authorId) {
        final Author author = em.find(Author.class, authorId);
        final BookInfo bookInfo = em.find(BookInfo.class, bookId);

        bookInfo.getAuthors().add(author);

        em.persist(bookInfo);
    }

    @Override
    public void addGenre(long bookId, long genreId) {
        final Genre genre = em.find(Genre.class, genreId);
        final BookInfo bookInfo = em.find(BookInfo.class, bookId);

        bookInfo.getGenres().add(genre);

        em.persist(bookInfo);
    }
}
