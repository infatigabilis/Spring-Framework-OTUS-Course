package ru.otus.springframework.hw29.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.springframework.hw29.domain.Genre;
import ru.otus.springframework.hw29.repository.GenreRepository;
import ru.otus.springframework.hw29.base.ValidationGroup;

import javax.validation.groups.Default;

@Controller @RequestMapping("genres")
public class GenreController {
    private static final String LIST_PAGE = "genre/list";
    private static final String ADD_PAGE = "genre/add";
    private static final String EDIT_PAGE = "genre/edit";

    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public String getAll(Model model) {
        model.addAttribute("data", genreRepository.findAll());
        return LIST_PAGE;
    }

    @GetMapping("add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddPage() {
        return ADD_PAGE;
    }

    @GetMapping("edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true)
    public String getEditPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("data", genreRepository.getOne(id));
        return EDIT_PAGE;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String add(@Validated({Default.class, ValidationGroup.Insert.class}) Genre genre) {
        genre.setId(null);
        genreRepository.save(genre);

        return "redirect:/genres";
    }

    @PostMapping("update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@Validated({Default.class, ValidationGroup.Update.class}) Genre genre) {
        genreRepository.save(genre);

        return "redirect:/genres";
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String remove(@PathVariable("id") long id) {
        genreRepository.deleteById(id);

        return "redirect:/genres";
    }
}
