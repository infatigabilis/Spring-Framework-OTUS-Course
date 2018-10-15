package ru.otus.springframework.hw17.web;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.springframework.hw17.base.ValidationGroup;
import ru.otus.springframework.hw17.domain.BookInfo;
import ru.otus.springframework.hw17.repository.AuthorRepository;
import ru.otus.springframework.hw17.repository.BookInfoRepository;
import ru.otus.springframework.hw17.repository.CommentRepository;
import ru.otus.springframework.hw17.repository.GenreRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;

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
    public Flux<BookInfo> getAll() {
        return bookInfoRepository.getAll();
    }

    @GetMapping("{id}")
    public Mono<BookInfo> getOne(@PathVariable("id") String id) {
        return bookInfoRepository.getOne(id);
    }

    @PostMapping
    public Mono<Void> add(@RequestBody @Validated({Default.class, ValidationGroup.Insert.class}) BookInfo book) {
        return bookInfoRepository.persist(book).then();
    }

    @PutMapping
    public Mono<Void> update(@RequestBody @Validated({Default.class, ValidationGroup.Update.class}) BookInfo book) {
        return bookInfoRepository.merge(book).then();
    }

    @DeleteMapping("{id}")
    public Mono<Void> remove(@PathVariable("id") String id) {
        return bookInfoRepository.deleteById(id).then();
    }


    @PostMapping("{bookId}/author/add")
    public Mono<Void> addAuthor(@NotBlank @PathVariable("bookId") String bookId, @NotBlank @RequestParam("author_id") String authorId) {
        return bookInfoRepository.addAuthorById(bookId, authorId).then();
    }

    @PostMapping("{bookId}/genre/add")
    public Mono<Void> addGenre(@NotBlank @PathVariable("bookId") String bookId, @NotBlank @RequestParam("genre_id") String genreId) {
        return bookInfoRepository.addGenreById(bookId, genreId).then();
    }

    @DeleteMapping("{bookId}/author/delete")
    public Mono<Void> deleteAuthor(@NotBlank @PathVariable("bookId") String bookId, @NotBlank @RequestParam("author_id") String authorId) {
        return bookInfoRepository.deleteAuthorById(bookId, authorId).then();
    }

    @DeleteMapping("{bookId}/genre/delete")
    public Mono<Void> deleteGenre(@NotBlank @PathVariable("bookId") String bookId, @NotBlank @RequestParam("genre_id") String genreId) {
        return bookInfoRepository.deleteGenreById(bookId, genreId).then();
    }
}
