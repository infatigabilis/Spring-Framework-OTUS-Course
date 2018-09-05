package ru.otus.springframework.hw17.repository.impl;

import org.springframework.context.annotation.Lazy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.springframework.hw17.domain.Author;
import ru.otus.springframework.hw17.domain.BookInfo;
import ru.otus.springframework.hw17.domain.Genre;
import ru.otus.springframework.hw17.persistence.BookInfoDocument;
import ru.otus.springframework.hw17.repository.AuthorRepository;
import ru.otus.springframework.hw17.repository.BookInfoRepository;
import ru.otus.springframework.hw17.repository.BookInfoRepositoryCustom;
import ru.otus.springframework.hw17.repository.GenreRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookInfoRepositoryCustomImpl implements BookInfoRepositoryCustom {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookInfoRepository bookInfoRepository;

    public BookInfoRepositoryCustomImpl(AuthorRepository authorRepository,
                                        GenreRepository genreRepository,
                                        @Lazy BookInfoRepository bookInfoRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookInfoRepository = bookInfoRepository;
    }

    @Override
    public Mono<BookInfo> getOne(String id) {
        return bookInfoRepository.findById(id).flatMap(this::map);
    }

    @Override
    public Flux<BookInfo> getAll() {
        return bookInfoRepository.findAll().flatMap(this::map);
    }

    @Override
    public Mono<BookInfo> persist(BookInfo bookInfo) {
        return bookInfoRepository.save(reverseMap(bookInfo)).flatMap(this::map);
    }

    @Override
    public Mono<Void> addAuthorById(String bookId, String authorId) {
        return bookInfoRepository.findById(bookId).flatMap(bookDoc -> {
            bookDoc.getAuthorIds().add(authorId);
            return bookInfoRepository.save(bookDoc).then();
        });
    }

    @Override
    public Mono<Void> addGenreById(String bookId, String genreId) {
        return bookInfoRepository.findById(bookId).flatMap(bookDoc -> {
            bookDoc.getGenreIds().add(genreId);
            return bookInfoRepository.save(bookDoc).then();
        });
    }

    @Override
    public Mono<Void> deleteAuthorById(String bookId, String authorId) {
        return bookInfoRepository.findById(bookId).flatMap(book -> {
            book.getAuthorIds().remove(authorId);
            return bookInfoRepository.save(book).then();
        });
    }

    @Override
    public Mono<Void> deleteGenreById(String bookId, String genreId) {
        return bookInfoRepository.findById(bookId).flatMap(bookDoc -> {
            bookDoc.getGenreIds().remove(genreId);
            return bookInfoRepository.save(bookDoc).then();
        });
    }

    @Override
    public Mono<Void> merge(BookInfo bookInfo) {
        return bookInfoRepository.findById(bookInfo.getId()).doOnNext(actualBook -> {
            actualBook.setTitle(bookInfo.getTitle());
            actualBook.setDescription(bookInfo.getDescription());
            bookInfoRepository.save(actualBook);
        }).then();
    }

    private Mono<BookInfo> map(BookInfoDocument document) {
        return Mono.zip(
                genreRepository.findAllById(document.getGenreIds()).collectList(),
                authorRepository.findAllById(document.getAuthorIds()).collectList(),

                (genres, authors) -> BookInfo.builder()
                            .id(document.getId())
                            .title(document.getTitle())
                            .description(document.getDescription())
                            .genres(genres)
                            .authors(authors)
                            .build()
        );
    }

    private BookInfoDocument reverseMap(BookInfo entity) {
        return BookInfoDocument.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .authorIds(
                        Optional.ofNullable(entity.getAuthors()).orElse(Collections.emptyList())
                                .stream().map(Author::getId).collect(Collectors.toList())
                )
                .genreIds(
                        Optional.ofNullable(entity.getGenres()).orElse(Collections.emptyList())
                                .stream().map(Genre::getId).collect(Collectors.toList())
                )
                .build();
    }
}
