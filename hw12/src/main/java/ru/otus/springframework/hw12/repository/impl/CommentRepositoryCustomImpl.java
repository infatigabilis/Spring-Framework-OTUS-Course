package ru.otus.springframework.hw12.repository.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springframework.hw12.domain.BookInfo;
import ru.otus.springframework.hw12.domain.Comment;
import ru.otus.springframework.hw12.repository.BookInfoRepository;
import ru.otus.springframework.hw12.repository.CommentRepository;
import ru.otus.springframework.hw12.repository.CommentRepositoryCustom;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {
    private final BookInfoRepository bookInfoRepository;
    private final CommentRepository commentRepository;

    public CommentRepositoryCustomImpl(BookInfoRepository bookInfoRepository, @Lazy CommentRepository commentRepository) {
        this.bookInfoRepository = bookInfoRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void addWithBookById(Comment comment, String bookId) {
        final BookInfo book = bookInfoRepository.findById(bookId).get();

        comment.setBookInfo(book);
        book.getComments().add(comment);

        commentRepository.save(comment);
        bookInfoRepository.save(book);
    }
}
