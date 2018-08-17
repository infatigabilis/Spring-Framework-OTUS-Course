package ru.otus.springframework.hw14.web;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.springframework.hw14.domain.Author;
import ru.otus.springframework.hw14.domain.BookInfo;
import ru.otus.springframework.hw14.domain.Comment;
import ru.otus.springframework.hw14.domain.Genre;
import ru.otus.springframework.hw14.repository.AuthorRepository;
import ru.otus.springframework.hw14.repository.BookInfoRepository;
import ru.otus.springframework.hw14.repository.CommentRepository;
import ru.otus.springframework.hw14.repository.GenreRepository;

import javax.validation.groups.Default;

import java.util.Date;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;
import static ru.otus.springframework.hw14.base.ValidationGroup.*;

@Controller @RequestMapping("books")
public class BookController {
    private static final String LIST_PAGE = "book/list";
    private static final String ONE_PAGE = "book/one";
    private static final String ADD_PAGE = "book/add";
    private static final String EDIT_PAGE = "book/edit";

    private static final String EMPTY_AUTHORS_VALUE = " - ";
    private static final String EMPTY_GENRES_VALUE = " - ";

    private final BookInfoRepository bookInfoRepository;
    private final CommentRepository commentRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookController(BookInfoRepository bookInfoRepository, CommentRepository commentRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
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
    public String getAddPage() {
        return ADD_PAGE;
    }

    @GetMapping("edit/{id}")
    @Transactional(readOnly = true)
    public String getEditPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("data", bookInfoRepository.getOne(id));
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return EDIT_PAGE;
    }

    @PostMapping
    public String add(@Validated({Default.class, Insert.class}) BookInfo book) {
        bookInfoRepository.save(book);

        return "redirect:/books/" + book.getId();
    }

    @PostMapping("update")
    public String update(@Validated({Default.class, Update.class}) BookInfo book) {
        bookInfoRepository.merge(book);

        return "redirect:/books/" + book.getId();
    }

    @PostMapping("delete/{id}")
    public String remove(@PathVariable("id") long id) {
        bookInfoRepository.deleteById(id);

        return "redirect:/books";
    }


    @PostMapping("add_comment/{bookId}")
    public String addComment(@PathVariable("bookId") long bookId, @Validated({Default.class, Insert.class}) Comment comment) {
        comment.setCreateAt(new Date());
        commentRepository.addWithBookById(comment, bookId);

        return "redirect:/books/" + bookId;
    }

    @PostMapping("add_author/{bookId}")
    public String addAuthor(@PathVariable("bookId") long bookId, @RequestParam("authorId") long authorId) {
        bookInfoRepository.addAuthorById(bookId, authorId);

        return "redirect:/books/" + bookId;
    }

    @PostMapping("add_genre/{bookId}")
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
