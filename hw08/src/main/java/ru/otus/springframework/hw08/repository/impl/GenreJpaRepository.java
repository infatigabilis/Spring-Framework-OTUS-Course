package ru.otus.springframework.hw08.dao.impl;

import org.springframework.stereotype.Repository;
import ru.otus.springframework.hw08.dao.GenreRepository;
import ru.otus.springframework.hw08.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GenreJpaRepository implements GenreRepository {
    private @PersistenceContext EntityManager em;

    @Override
    public List<Genre> getAll() {
        return em.createQuery("from Genre", Genre.class).getResultList();
    }

    @Override
    public void add(Genre genre) {
        em.persist(genre);
    }
}
