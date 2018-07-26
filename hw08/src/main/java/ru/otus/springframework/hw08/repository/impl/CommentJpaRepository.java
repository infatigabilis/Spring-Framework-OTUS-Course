package ru.otus.springframework.hw08.repository.impl;

import org.springframework.stereotype.Repository;
import ru.otus.springframework.hw08.domain.BookInfo;
import ru.otus.springframework.hw08.domain.Comment;
import ru.otus.springframework.hw08.repository.BookInfoRepository;
import ru.otus.springframework.hw08.repository.CommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class CommentJpaRepository implements CommentRepository {
    private final BookInfoRepository bookInfoRepository;
    private @PersistenceContext EntityManager em;

    public CommentJpaRepository(BookInfoRepository bookInfoRepository) {
        this.bookInfoRepository = bookInfoRepository;
    }

    @Override
    @Transactional
    public void add(Comment comment, long bookId) {
        comment.setBookInfo(bookInfoRepository.getById(bookId));
        em.persist(comment);
    }
}
