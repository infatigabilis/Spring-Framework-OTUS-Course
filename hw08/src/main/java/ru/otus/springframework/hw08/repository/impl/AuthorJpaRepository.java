package ru.otus.springframework.hw08.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springframework.hw08.domain.Author;
import ru.otus.springframework.hw08.repository.AuthorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Min;
import java.util.List;

@Repository
public class AuthorJpaRepository implements AuthorRepository {
    private @PersistenceContext EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAll() {
        return em.createQuery("from Author", Author.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Author getById(@Min(0) long id) {
        return em.find(Author.class, id);
    }

    @Override
    @Transactional
    public void add(Author author) {
        em.persist(author);
    }
}
