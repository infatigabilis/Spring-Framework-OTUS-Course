package ru.otus.springframework.hw06.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.springframework.hw06.dao.BookInfoDao;
import ru.otus.springframework.hw06.domain.Author;
import ru.otus.springframework.hw06.domain.BookInfo;
import ru.otus.springframework.hw06.domain.Genre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

@ShellComponent
public class BookCommands {
    private static final String EMPTY_AUTHORS_VALUE = "Various authors";
    private static final String EMPTY_GENRES_VALUE = "-";
    private static final String EMPTY_LIBRARY_VALUE = "Library is empty...";
    private static final String OK_VALUE = "OK";

    private static final int TEXT_LENGTH = 50;

    private final BookInfoDao bookInfoDao;

    public BookCommands(BookInfoDao bookInfoDao) {
        this.bookInfoDao = bookInfoDao;
    }


    @ShellMethod("Get a list of all books")
    public String allBooks() {
        return summaryPrintBooks(bookInfoDao.getAll());
    }

    @ShellMethod("Get a specific book by id")
    public String book(@ShellOption long id) {
        return fullPrintBook(bookInfoDao.getById(id));
    }

    @ShellMethod("Add new book")
    public String addBook(@ShellOption String title, @ShellOption(defaultValue = "") String desc) {
        bookInfoDao.insert(new BookInfo() {{
            setTitle(title);
            setDescription(desc);
        }});

        return OK_VALUE;
    }

    @ShellMethod("Add new author to the book")
    public String addAuthorToBook(@ShellOption long authorId, @ShellOption long bookId) {
        bookInfoDao.addAuthor(bookId, authorId);
        return OK_VALUE;
    }

    @ShellMethod("Add new genre to the book")
    public String addGenreToBook(@ShellOption long genreId, @ShellOption long bookId) {
        bookInfoDao.addGenre(bookId, genreId);
        return OK_VALUE;
    }


    private String summaryPrintBooks(List<BookInfo> books) {
        return books.stream()
                .map(this::summaryPrintBook)
                .reduce((s1, s2) -> s1 + "\n" + s2)
                .orElse(EMPTY_LIBRARY_VALUE);
    }

    private String fullPrintBook(BookInfo book) {
        return Arrays.stream(
            new String[] {
                String.format("[id: %s]", book.getId()),
                "",
                String.format("\"%s\"", book.getTitle()),
                String.format("\tby %s", printAuthors(book.getAuthors())),
                "",
                String.format("Genres: %s", printGenres(book.getGenres())),
                "",
                printDescription(book.getDescription()),
                ""
            }
        )
                .reduce((s1, s2) -> s1 + "\n" + s2)
                .orElse("");
    }

    private String printDescription(String desc) {
        if (desc == null) {
            return "";
        }

        if (desc.length() <= TEXT_LENGTH) {
            return desc;
        }

        final List<String> result = new ArrayList<>();

        while (desc.length() > TEXT_LENGTH) {
            final int nextSpaceIndex = desc.indexOf(" ", TEXT_LENGTH);

            if (nextSpaceIndex == -1) {
                result.add(desc);
                break;
            }

            result.add(desc.substring(0, nextSpaceIndex));
            desc = desc.substring(nextSpaceIndex+1);
        }

        return result.stream().reduce((s1, s2) -> s1 + "\n" + s2).orElse("");
    }

    private String summaryPrintBook(BookInfo book) {
        return String.format("[%s] \"%s\" by %s", book.getId(), book.getTitle(), printAuthors(book.getAuthors()));
    }

    private String printAuthors(List<Author> authors) {
        return authors.stream().map(this::printAuthor).reduce((s1, s2) -> s1 + ", " + s2).orElse(EMPTY_AUTHORS_VALUE);
    }

    private String printAuthor(Author author) {
        if (!isEmpty(author.getSurname())) {
            return author.getSurname() + " " + author.getName().charAt(0) + ".";
        }
        return author.getName();
    }

    private String printGenres(List<Genre> genres) {
        return genres.stream().map(Genre::getName).reduce((s1, s2) -> s1 + ", " + s2).orElse(EMPTY_GENRES_VALUE);
    }
}
