package ru.otus.springframework.hw17.repository.impl;

import org.springframework.context.annotation.Lazy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.springframework.hw17.domain.Comment;
import ru.otus.springframework.hw17.persistence.CommentDocument;
import ru.otus.springframework.hw17.repository.BookInfoRepository;
import ru.otus.springframework.hw17.repository.CommentRepository;
import ru.otus.springframework.hw17.repository.CommentRepositoryCustom;

import javax.validation.constraints.NotBlank;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {
    private final BookInfoRepository bookInfoRepository;
    private final CommentRepository commentRepository;

    public CommentRepositoryCustomImpl(BookInfoRepository bookInfoRepository, @Lazy CommentRepository commentRepository) {
        this.bookInfoRepository = bookInfoRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Mono<Comment> addWithBookById(Comment comment, String bookId) {
        CommentDocument doc = reverseMap(comment);
        doc.setBookInfoId(bookId);
        return commentRepository.save(doc).flatMap(this::map);
    }

    @Override
    public Flux<Comment> getByBookInfoId(@NotBlank String bookId) {
        return commentRepository.findByBookInfoId(bookId).flatMap(this::map);
    }

    private Mono<Comment> map(CommentDocument doc) {
        return bookInfoRepository.getOne(doc.getBookInfoId()).map(bookInfo -> Comment.builder()
                .id(doc.getId())
                .text(doc.getText())
                .createAt(doc.getCreateAt())
                .bookInfo(bookInfo)
                .build()
        );
    }

    private CommentDocument reverseMap(Comment entity) {
        return CommentDocument.builder()
                .id(entity.getId())
                .text(entity.getText())
                .createAt(entity.getCreateAt())
                .bookInfoId(entity.getId())
                .build();
    }
}
