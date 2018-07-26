package ru.otus.springframework.hw08.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springframework.hw08.domain.Genre;
import ru.otus.springframework.hw08.repository.GenreRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Min;
import java.util.List;

@Repository
public class GenreJpaRepository implements GenreRepository {
    private @PersistenceContext EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAll() {
        return em.createQuery("from Genre", Genre.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getById(@Min(0) long id) {
        return em.find(Genre.class, id);
    }

    @Override
    @Transactional
    public void add(Genre genre) {
        em.persist(genre);
    }
}
