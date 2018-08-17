package ru.otus.springframework.hw14.web;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.springframework.hw14.domain.Author;
import ru.otus.springframework.hw14.repository.AuthorRepository;

import javax.validation.Valid;
import javax.validation.groups.Default;

import static ru.otus.springframework.hw14.base.ValidationGroup.*;

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
    public String getAddPage() {
        return ADD_PAGE;
    }

    @GetMapping("edit/{id}")
    @Transactional(readOnly = true)
    public String getEditPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("data", authorRepository.getOne(id));
        return EDIT_PAGE;
    }

    @PostMapping
    public String add(@Validated({Default.class, Insert.class}) Author author) {
        author.setId(null);
        authorRepository.save(author);

        return "redirect:/authors";
    }

    @PostMapping("update")
    public String update(@Validated({Default.class, Update.class}) Author author) {
        authorRepository.save(author);

        return "redirect:/authors";
    }

    @PostMapping("delete/{id}")
    public String remove(@PathVariable("id") long id) {
        authorRepository.deleteById(id);

        return "redirect:/authors";
    }
}
