package ru.otus.springframework.hw08.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.springframework.hw08.dao.AuthorRepository;
import ru.otus.springframework.hw08.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AuthorJpaRepository implements AuthorRepository {
    private @PersistenceContext EntityManager em;

    @Override
    public List<Author> getAll() {
        return em.createQuery("from Author", Author.class).getResultList();
    }

    @Override
    public void add(Author author) {
        em.persist(author);
    }
}
