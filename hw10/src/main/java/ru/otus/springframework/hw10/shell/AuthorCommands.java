package ru.otus.springframework.hw10.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.springframework.hw10.domain.Author;
import ru.otus.springframework.hw10.repository.AuthorRepository;

import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

@ShellComponent
public class AuthorCommands {
    private static final String EMPTY_AUTHOR_LIST_VALUE = "There is no author...";
    private static final String OK_VALUE = "OK";

    private final AuthorRepository authorRepository;

    public AuthorCommands(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @ShellMethod("Get a list of all authors")
    public String allAuthors() {
        return printAuthors(authorRepository.findAll());
    }

    @ShellMethod("Add new author")
    public String addAuthor(@ShellOption String name, @ShellOption(defaultValue = "") String surname) {
        authorRepository.save(Author.builder().name(name).surname(surname).build());
        return OK_VALUE;
    }

    private String printAuthors(List<Author> authors) {
        return authors.stream()
                .map(this::printAuthor)
                .reduce((s1, s2) -> s1 + "\n" + s2)
                .orElse(EMPTY_AUTHOR_LIST_VALUE);
    }

    private String printAuthor(Author author) {
        if (isEmpty(author.getSurname())) {
            return String.format("[%s] %s", author.getId(), author.getName());
        }

        return String.format("[%s] %s %s", author.getId(), author.getName(), author.getSurname());
    }
}
