package ru.otus.springframework.hw17.web;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.springframework.hw17.domain.Comment;
import ru.otus.springframework.hw17.repository.CommentRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;

import static ru.otus.springframework.hw17.base.ValidationGroup.Insert;

@RestController @RequestMapping("comments")
public class CommentController {
    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping
    public Flux<Comment> getByBookId(@NotBlank @RequestParam("book_id") String bookId) {
        return commentRepository.getByBookInfoId(bookId);
    }

    @PostMapping
    public Mono<Void> addComment(@NotBlank @RequestParam("book_id") String bookId,
                                 @RequestBody @Validated({Default.class, Insert.class}) Comment comment) {
        return commentRepository.addWithBookById(comment, bookId).then();
    }
}
