package ru.otus.springframework.hw17.web;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.springframework.hw17.base.ValidationGroup;
import ru.otus.springframework.hw17.domain.Author;
import ru.otus.springframework.hw17.repository.AuthorRepository;

import javax.validation.groups.Default;

@RestController @RequestMapping("authors")
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public Flux<Author> getAll() {
        return authorRepository.findAll();
    }

    @GetMapping("{id}")
    public Mono<Author> getOne(@PathVariable("id") String id) {
        return authorRepository.findById(id);
    }

    @PostMapping
    public Mono<Void> add(@RequestBody @Validated({Default.class, ValidationGroup.Insert.class}) Author author) {
        return authorRepository.save(author).then();
    }

    @PutMapping
    public Mono<Void> update(@RequestBody @Validated({Default.class, ValidationGroup.Update.class}) Author author) {
        return authorRepository.save(author).then();
    }

    @DeleteMapping("{id}")
    public Mono<Void> remove(@PathVariable("id") String id) {
        return authorRepository.deleteById(id).then();
    }
}
