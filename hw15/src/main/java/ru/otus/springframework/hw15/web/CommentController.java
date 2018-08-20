package ru.otus.springframework.hw15.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.springframework.hw15.domain.Comment;
import ru.otus.springframework.hw15.repository.CommentRepository;

import javax.validation.constraints.Min;
import javax.validation.groups.Default;

import static ru.otus.springframework.hw15.base.ValidationGroup.Insert;

@RestController @RequestMapping("comments")
public class CommentController {
    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("{bookId}")
    public Page<Comment> getByBookId(@PathVariable("bookId") long bookId,
                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return commentRepository.findByBookInfoId(bookId, PageRequest.of(page, size));
    }

    @PostMapping("{bookId}")
    public void addComment(@Min(1) @PathVariable("bookId") long bookId,
                           @RequestBody @Validated({Default.class, Insert.class}) Comment comment) {
        commentRepository.addWithBookById(comment, bookId);
    }
}
