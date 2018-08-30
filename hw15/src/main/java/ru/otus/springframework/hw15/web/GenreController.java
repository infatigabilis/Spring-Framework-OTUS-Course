package ru.otus.springframework.hw15.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.springframework.hw15.domain.Genre;
import ru.otus.springframework.hw15.repository.GenreRepository;

import javax.validation.groups.Default;

import java.util.List;

import static ru.otus.springframework.hw15.base.ValidationGroup.*;

@RestController @RequestMapping("genres")
public class GenreController {
    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping
    public Page<Genre> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        return genreRepository.findAll(PageRequest.of(page, size));
    }

    @GetMapping("{id}")
    public Genre getOne(@PathVariable("id") long id) {
        return genreRepository.findById(id).get();
    }

    @PostMapping
    public void add(@RequestBody @Validated({Default.class, Insert.class}) Genre genre) {
        genreRepository.save(genre);
    }

    @PutMapping
    public void update(@RequestBody @Validated({Default.class, Update.class}) Genre genre) {
        genreRepository.save(genre);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable("id") long id) {
        genreRepository.deleteById(id);
    }
}
