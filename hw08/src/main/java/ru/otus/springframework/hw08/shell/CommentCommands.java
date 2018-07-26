package ru.otus.springframework.hw08.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.springframework.hw08.domain.Comment;
import ru.otus.springframework.hw08.domain.Genre;
import ru.otus.springframework.hw08.repository.CommentRepository;

import java.util.List;

@ShellComponent
public class CommentCommands {
    private static final String OK_VALUE = "OK";

    private final CommentRepository commentRepository;

    public CommentCommands(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @ShellMethod("Add new comment to book")
    public String addComment(@ShellOption String text, @ShellOption long bookId) {
        commentRepository.add(Comment.builder().text(text).build(), bookId);
        return OK_VALUE;
    }
}
