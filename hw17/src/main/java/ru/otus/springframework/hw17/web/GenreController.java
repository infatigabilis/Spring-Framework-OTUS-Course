package ru.otus.springframework.hw17.web;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.springframework.hw17.base.ValidationGroup;
import ru.otus.springframework.hw17.domain.Genre;
import ru.otus.springframework.hw17.repository.GenreRepository;

import javax.validation.groups.Default;

@RestController @RequestMapping("genres")
public class GenreController {
    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping
    public Flux<Genre> getAll() {
        return genreRepository.findAll();
    }

    @GetMapping("{id}")
    public Mono<Genre> getOne(@PathVariable("id") String id) {
        return genreRepository.findById(id);
    }

    @PostMapping
    public Mono<Void> add(@RequestBody @Validated({Default.class, ValidationGroup.Insert.class}) Genre genre) {
        return genreRepository.save(genre).then();
    }

    @PutMapping
    public Mono<Void> update(@RequestBody @Validated({Default.class, ValidationGroup.Update.class}) Genre genre) {
        return genreRepository.save(genre).then();
    }

    @DeleteMapping("{id}")
    public Mono<Void> remove(@PathVariable("id") String id) {
        return genreRepository.deleteById(id).then();
    }
}
