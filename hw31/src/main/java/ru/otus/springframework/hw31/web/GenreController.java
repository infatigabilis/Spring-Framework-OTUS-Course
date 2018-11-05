package ru.otus.springframework.hw31.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.springframework.hw31.domain.Genre;
import ru.otus.springframework.hw31.service.GenreService;

@Controller @RequestMapping("genres")
public class GenreController {
    private static final String LIST_PAGE = "genre/list";
    private static final String ADD_PAGE = "genre/add";
    private static final String EDIT_PAGE = "genre/edit";

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("data", genreService.getAll());
        return LIST_PAGE;
    }

    @GetMapping("add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddPage() {
        return ADD_PAGE;
    }

    @GetMapping("edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getEditPage(@PathVariable long id, Model model) {
        model.addAttribute("data", genreService.get(id));
        return EDIT_PAGE;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String add(Genre genre) {
        genreService.add(genre);
        return "redirect:/genres";
    }

    @PostMapping("update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(Genre genre) {
        genreService.update(genre);
        return "redirect:/genres";
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String remove(@PathVariable long id) {
        genreService.remove(id);
        return "redirect:/genres";
    }
}
