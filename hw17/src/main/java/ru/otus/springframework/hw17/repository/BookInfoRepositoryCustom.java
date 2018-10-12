package ru.otus.springframework.hw17.repository;

import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.springframework.hw17.domain.BookInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;

import static ru.otus.springframework.hw17.base.ValidationGroup.Update;

@Validated
public interface BookInfoRepositoryCustom {
    Mono<BookInfo> getOne(String id);
    Flux<BookInfo> getAll();
    Mono<BookInfo> persist(BookInfo bookInfo);
    Mono<Void> addAuthorById(@NotBlank String bookId, @NotBlank String authorId);
    Mono<Void> addGenreById(@NotBlank String bookId, @NotBlank String genreId);
    Mono<Void> deleteAuthorById(@NotBlank String bookId, @NotBlank String authorId);
    Mono<Void> deleteGenreById(@NotBlank String bookId, @NotBlank String genreId);
    Mono<Void> merge(@Validated({Default.class, Update.class}) BookInfo bookInfo);
}
