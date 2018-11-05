package ru.otus.springframework.hw31.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.springframework.hw31.domain.Author;
import ru.otus.springframework.hw31.service.AuthorService;

@Controller @RequestMapping("authors")
public class AuthorController {
    private static final String LIST_PAGE = "author/list";
    private static final String ADD_PAGE = "author/add";
    private static final String EDIT_PAGE = "author/edit";

    private final AuthorService authorService;


    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("data", authorService.getAll());
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
        model.addAttribute("data", authorService.get(id));
        return EDIT_PAGE;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String add(Author author) {
        authorService.add(author);
        return "redirect:/authors";
    }

    @PostMapping("update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(Author author) {
        authorService.update(author);
        return "redirect:/authors";
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String remove(@PathVariable long id) {
        authorService.remove(id);
        return "redirect:/authors";
    }
}
