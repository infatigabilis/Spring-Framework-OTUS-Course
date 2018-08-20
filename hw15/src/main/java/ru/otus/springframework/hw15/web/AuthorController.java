package ru.otus.springframework.hw15.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.springframework.hw15.domain.Author;
import ru.otus.springframework.hw15.repository.AuthorRepository;

import javax.validation.groups.Default;

import java.util.List;

import static ru.otus.springframework.hw15.base.ValidationGroup.*;

@RestController @RequestMapping("authors")
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public Page<Author> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {
        return authorRepository.findAll(PageRequest.of(page, size));
    }

    @GetMapping("{id}")
    public Author getOne(@PathVariable("id") long id) {
        return authorRepository.findById(id).get();
    }

    @PostMapping
    public void add(@RequestBody @Validated({Default.class, Insert.class}) Author author) {
        authorRepository.save(author);
    }

    @PutMapping
    public void update(@RequestBody @Validated({Default.class, Update.class}) Author author) {
        authorRepository.save(author);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable("id") long id) {
        authorRepository.deleteById(id);
    }
}
