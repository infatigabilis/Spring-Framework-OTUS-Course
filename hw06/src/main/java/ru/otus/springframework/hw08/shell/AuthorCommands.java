package ru.otus.springframework.hw06.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.springframework.hw06.dao.AuthorDao;
import ru.otus.springframework.hw06.domain.Author;

import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

@ShellComponent
public class AuthorCommands {
    private static final String EMPTY_AUTHOR_LIST_VALUE = "There is no author...";
    private static final String OK_VALUE = "OK";

    private final AuthorDao authorDao;

    public AuthorCommands(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }


    @ShellMethod("Get a list of all authors")
    public String allAuthors() {
        return printAuthors(authorDao.getAll());
    }

    @ShellMethod("Add new author")
    public String addAuthor(@ShellOption String name, @ShellOption(defaultValue = "") String surname) {
        authorDao.insert(new Author() {{
            setName(name);
            setSurname(surname);
        }});

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
