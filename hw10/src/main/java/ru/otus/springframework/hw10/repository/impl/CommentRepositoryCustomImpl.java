package ru.otus.springframework.hw10.repository.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springframework.hw10.domain.Comment;
import ru.otus.springframework.hw10.repository.BookInfoRepository;
import ru.otus.springframework.hw10.repository.CommentRepository;
import ru.otus.springframework.hw10.repository.CommentRepositoryCustom;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {
    private final BookInfoRepository bookInfoRepository;
    private final CommentRepository commentRepository;

    public CommentRepositoryCustomImpl(BookInfoRepository bookInfoRepository, @Lazy CommentRepository commentRepository) {
        this.bookInfoRepository = bookInfoRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void addWithBookById(Comment comment, long bookId) {
        comment.setBookInfo(bookInfoRepository.findById(bookId).get());
        commentRepository.save(comment);
    }
}
