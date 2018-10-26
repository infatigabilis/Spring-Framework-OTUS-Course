package ru.otus.springframework.hw29.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.springframework.hw29.base.ValidationGroup;
import ru.otus.springframework.hw29.domain.Author;
import ru.otus.springframework.hw29.repository.AuthorRepository;

import javax.validation.groups.Default;

@Controller @RequestMapping("authors")
public class AuthorController {
    private static final String LIST_PAGE = "author/list";
    private static final String ADD_PAGE = "author/add";
    private static final String EDIT_PAGE = "author/edit";

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public String getAll(Model model) {
        model.addAttribute("data", authorRepository.findAll());
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
        model.addAttribute("data", authorRepository.getOne(id));
        return EDIT_PAGE;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String add(@Validated({Default.class, ValidationGroup.Insert.class}) Author author, Authentication authentication) {
        author.setId(null);
        Author saved = authorRepository.save(author);



        return "redirect:/authors";
    }

    @PostMapping("update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@Validated({Default.class, ValidationGroup.Update.class}) Author author) {
        authorRepository.save(author);

        return "redirect:/authors";
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String remove(@PathVariable("id") long id) {
        authorRepository.deleteById(id);

        return "redirect:/authors";
    }
}
