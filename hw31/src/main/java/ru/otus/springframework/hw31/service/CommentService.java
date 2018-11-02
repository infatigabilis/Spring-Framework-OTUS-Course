package ru.otus.springframework.hw31.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.otus.springframework.hw31.domain.base.ValidationGroup;
import ru.otus.springframework.hw31.domain.Comment;
import ru.otus.springframework.hw31.exception.ServiceUnavailableException;
import ru.otus.springframework.hw31.repository.BookInfoRepository;
import ru.otus.springframework.hw31.repository.CommentRepository;

import javax.validation.groups.Default;

@Service
public class CommentService {
    private final BookInfoRepository bookInfoRepository;
    private final CommentRepository commentRepository;

    public CommentService(BookInfoRepository bookInfoRepository, CommentRepository commentRepository) {
        this.bookInfoRepository = bookInfoRepository;
        this.commentRepository = commentRepository;
    }

    @HystrixCommand(fallbackMethod = "addFallback")
    public void add(@Validated({Default.class, ValidationGroup.Insert.class}) Comment comment, long bookId) {
        comment.setBookInfo(bookInfoRepository.findById(bookId).get());
        commentRepository.save(comment);
    }

    private void addFallback(Comment comment, long bookId, Throwable cause) {
        throw new ServiceUnavailableException("Comment DB", cause);
    }
}
