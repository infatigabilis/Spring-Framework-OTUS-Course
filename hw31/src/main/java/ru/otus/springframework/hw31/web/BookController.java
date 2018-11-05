package ru.otus.springframework.hw31.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.springframework.hw31.domain.Author;
import ru.otus.springframework.hw31.domain.BookInfo;
import ru.otus.springframework.hw31.domain.Comment;
import ru.otus.springframework.hw31.domain.Genre;
import ru.otus.springframework.hw31.service.AuthorService;
import ru.otus.springframework.hw31.service.BookService;
import ru.otus.springframework.hw31.service.CommentService;
import ru.otus.springframework.hw31.service.GenreService;

import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

@Controller @RequestMapping("books")
public class BookController {
    private static final String LIST_PAGE = "book/list";
    private static final String ONE_PAGE = "book/one";
    private static final String ADD_PAGE = "book/add";
    private static final String EDIT_PAGE = "book/edit";

    private static final String EMPTY_AUTHORS_VALUE = " - ";
    private static final String EMPTY_GENRES_VALUE = " - ";

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    public BookController(BookService bookService, AuthorService authorService, GenreService genreService, CommentService commentService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("data", bookService.getAll());
        return LIST_PAGE;
    }

    @GetMapping("{id}")
    public String getOne(@PathVariable long id, Model model) {
        model.addAttribute("data", bookService.get(id));
        return ONE_PAGE;
    }

    @GetMapping("add")
    @PreAuthorize("isAuthenticated()")
    public String getAddPage() {
        return ADD_PAGE;
    }

    @GetMapping("edit/{id}")
    @PreAuthorize("hasPermission(#id, 'ru.otus.springframework.hw31.domain.BookInfo', 'WRITE')")
    public String getEditPage(@PathVariable long id, Model model) {
        model.addAttribute("data", bookService.get(id));
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("genres", genreService.getAll());
        return EDIT_PAGE;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public String add(BookInfo book, Authentication authentication) {
        bookService.add(book, authentication);
        return "redirect:/books/" + book.getId();
    }

    @PostMapping("update")
    @PreAuthorize("hasPermission(#book.id, 'ru.otus.springframework.hw31.domain.BookInfo', 'WRITE')")
    public String update(BookInfo book) {
        bookService.update(book);
        return "redirect:/books/" + book.getId();
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasPermission(#id, 'ru.otus.springframework.hw31.domain.BookInfo', 'DELETE')")
    public String remove(@PathVariable long id) {
        bookService.remove(id);
        return "redirect:/books";
    }


    @PostMapping("add_comment/{bookId}")
    public String addComment(@PathVariable long bookId, Comment comment) {
        commentService.add(comment, bookId);
        return "redirect:/books/" + bookId;
    }

    @PostMapping("add_author/{bookId}")
    @PreAuthorize("hasPermission(#bookId, 'ru.otus.springframework.hw31.domain.BookInfo', 'WRITE')")
    public String addAuthor(@PathVariable long bookId, @RequestParam long authorId) {
        authorService.bindToBook(authorId, bookId);
        return "redirect:/books/" + bookId;
    }

    @PostMapping("add_genre/{bookId}")
    @PreAuthorize("hasPermission(#bookId, 'ru.otus.springframework.hw31.domain.BookInfo', 'WRITE')")
    public String addGenre(@PathVariable long bookId, @RequestParam long genreId) {
        genreService.bindToBook(genreId, bookId);
        return "redirect:/books/" + bookId;
    }


    public static String printAuthors(List<Author> authors) {
        if (authors == null) {
            return EMPTY_AUTHORS_VALUE;
        }

        return authors.stream()
                .map(author -> {
                    if (!isEmpty(author.getSurname())) {
                        return author.getSurname() + " " + author.getName().charAt(0) + ".";
                    }
                    return author.getName();
                })
                .reduce((s1, s2) -> s1 + ", " + s2).orElse(EMPTY_AUTHORS_VALUE);
    }

    public static String printGenres(List<Genre> genres) {
        if (genres == null) {
            return EMPTY_GENRES_VALUE;
        }

        return genres.stream().map(Genre::getName).reduce((s1, s2) -> s1 + ", " + s2).orElse(EMPTY_GENRES_VALUE);
    }
}
