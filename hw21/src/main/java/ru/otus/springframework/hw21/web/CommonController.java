package ru.otus.springframework.hw21.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class CommonController {
    private static final String ERROR_403_PAGE = "error/403";

    @GetMapping
    public String home() {
        return "redirect:/books";
    }

    @RequestMapping(path = "403", method = {GET, POST})
    public String error403() {
        return ERROR_403_PAGE;
    }
}
