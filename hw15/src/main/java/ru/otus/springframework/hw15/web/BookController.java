package ru.otus.springframework.hw15.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.springframework.hw15.domain.Author;
import ru.otus.springframework.hw15.domain.BookInfo;
import ru.otus.springframework.hw15.domain.Comment;
import ru.otus.springframework.hw15.domain.Genre;
import ru.otus.springframework.hw15.repository.AuthorRepository;
import ru.otus.springframework.hw15.repository.BookInfoRepository;
import ru.otus.springframework.hw15.repository.CommentRepository;
import ru.otus.springframework.hw15.repository.GenreRepository;

import javax.validation.constraints.Min;
import javax.validation.groups.Default;

import java.util.Date;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;
import static ru.otus.springframework.hw15.base.ValidationGroup.*;

@RestController @RequestMapping("books")
public class BookController {
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
    public Page<BookInfo> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        return bookInfoRepository.findAll(PageRequest.of(page, size));
    }

    @GetMapping("{id}")
    public BookInfo getOne(@PathVariable("id") long id) {
        return bookInfoRepository.findById(id).get();
    }

    @PostMapping
    public void add(@RequestBody @Validated({Default.class, Insert.class}) BookInfo book) {
        bookInfoRepository.save(book);
    }

    @PutMapping
    public void update(@RequestBody @Validated({Default.class, Update.class}) BookInfo book) {
        bookInfoRepository.merge(book);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable("id") long id) {
        bookInfoRepository.deleteById(id);
    }


    @PostMapping("add_author/{bookId}/{authorId}")
    public void addAuthor(@Min(1) @PathVariable("bookId") long bookId, @Min(1) @PathVariable("authorId") long authorId) {
        bookInfoRepository.addAuthorById(bookId, authorId);
    }

    @PostMapping("add_genre/{bookId}/{genreId}")
    public void addGenre(@Min(1) @PathVariable("bookId") long bookId, @Min(1) @PathVariable("genreId") long genreId) {
        bookInfoRepository.addGenreById(bookId, genreId);
    }

    @DeleteMapping("delete_author/{bookId}/{authorId}")
    public void deleteAuthor(@Min(1) @PathVariable("bookId") long bookId, @Min(1) @PathVariable("authorId") long authorId) {
        bookInfoRepository.deleteAuthorById(bookId, authorId);
    }

    @DeleteMapping("delete_author/{bookId}/{genreId}")
    public void deleteGenre(@Min(1) @PathVariable("bookId") long bookId, @Min(1) @PathVariable("genreId") long genreId) {
        bookInfoRepository.deleteGenreById(bookId, genreId);
    }
}
