package ru.otus.springframework.hw12.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.springframework.hw12.domain.Genre;
import ru.otus.springframework.hw12.repository.GenreRepository;

import java.util.List;

@ShellComponent
public class GenreCommands {
    private static final String EMPTY_GENRE_LIST_VALUE = "There is no genre...";
    private static final String OK_VALUE = "OK";

    private final GenreRepository genreDao;

    public GenreCommands(GenreRepository genreDao) {
        this.genreDao = genreDao;
    }


    @ShellMethod("Get a list of all genres")
    public String allGenres() {
        return printGenres(genreDao.findAll());
    }

    @ShellMethod("Add new genre")
    public String addGenre(@ShellOption String name) {
        genreDao.save(Genre.builder().name(name).build());
        return OK_VALUE;
    }

    private String printGenres(List<Genre> genres) {
        return genres.stream()
                .map(this::printGenre)
                .reduce((s1, s2) -> s1 + "\n" + s2)
                .orElse(EMPTY_GENRE_LIST_VALUE);
    }

    private String printGenre(Genre author) {
        return String.format("[%s] %s", author.getId(), author.getName());
    }
}
