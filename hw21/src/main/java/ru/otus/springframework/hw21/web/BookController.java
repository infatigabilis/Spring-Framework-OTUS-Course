package ru.otus.springframework.hw21.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.springframework.hw21.domain.Author;
import ru.otus.springframework.hw21.domain.BookInfo;
import ru.otus.springframework.hw21.domain.Comment;
import ru.otus.springframework.hw21.domain.Genre;
import ru.otus.springframework.hw21.repository.AuthorRepository;
import ru.otus.springframework.hw21.repository.BookInfoRepository;
import ru.otus.springframework.hw21.repository.CommentRepository;
import ru.otus.springframework.hw21.repository.GenreRepository;
import ru.otus.springframework.hw21.base.ValidationGroup;
import ru.otus.springframework.hw21.service.AuthorizeService;

import javax.validation.groups.Default;

import java.util.Date;
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

    private final AuthorizeService authorizeService;
    private final BookInfoRepository bookInfoRepository;
    private final CommentRepository commentRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookController(AuthorizeService authorizeService, BookInfoRepository bookInfoRepository, CommentRepository commentRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.authorizeService = authorizeService;
        this.bookInfoRepository = bookInfoRepository;
        this.commentRepository = commentRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public String getAll(Model model) {
        model.addAttribute("data", bookInfoRepository.findAll());
        return LIST_PAGE;
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public String getOne(@PathVariable("id") long id, Model model) {
        model.addAttribute("data", bookInfoRepository.getOne(id));
        return ONE_PAGE;
    }

    @GetMapping("add")
    @PreAuthorize("isAuthenticated()")
    public String getAddPage() {
        return ADD_PAGE;
    }

    @GetMapping("edit/{id}")
    @PreAuthorize("hasPermission(#id, 'ru.otus.springframework.hw21.domain.BookInfo', 'WRITE')")
    @Transactional(readOnly = true)
    public String getEditPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("data", bookInfoRepository.getOne(id));
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return EDIT_PAGE;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public String add(@Validated({Default.class, ValidationGroup.Insert.class}) BookInfo book, Authentication authentication) {
        BookInfo saved = bookInfoRepository.save(book);
        authorizeService.createAcl(saved, authentication);

        return "redirect:/books/" + book.getId();
    }

    @PostMapping("update")
    @PreAuthorize("hasPermission(#book.id, 'ru.otus.springframework.hw21.domain.BookInfo', 'WRITE')")
    public String update(@Validated({Default.class, ValidationGroup.Update.class}) BookInfo book) {
        bookInfoRepository.merge(book);

        return "redirect:/books/" + book.getId();
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasPermission(#id, 'ru.otus.springframework.hw21.domain.BookInfo', 'DELETE')")
    public String remove(@PathVariable("id") long id) {
        bookInfoRepository.deleteById(id);

        return "redirect:/books";
    }


    @PostMapping("add_comment/{bookId}")
    public String addComment(@PathVariable("bookId") long bookId, @Validated({Default.class, ValidationGroup.Insert.class}) Comment comment) {
        comment.setCreateAt(new Date());
        commentRepository.addWithBookById(comment, bookId);

        return "redirect:/books/" + bookId;
    }

    @PostMapping("add_author/{bookId}")
    @PreAuthorize("hasPermission(#bookId, 'ru.otus.springframework.hw21.domain.BookInfo', 'WRITE')")
    public String addAuthor(@PathVariable("bookId") long bookId, @RequestParam("authorId") long authorId) {
        bookInfoRepository.addAuthorById(bookId, authorId);

        return "redirect:/books/" + bookId;
    }

    @PostMapping("add_genre/{bookId}")
    @PreAuthorize("hasPermission(#bookId, 'ru.otus.springframework.hw21.domain.BookInfo', 'WRITE')")
    public String addGenre(@PathVariable("bookId") long bookId, @RequestParam("genreId") long genreId) {
        bookInfoRepository.addGenreById(bookId, genreId);

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
