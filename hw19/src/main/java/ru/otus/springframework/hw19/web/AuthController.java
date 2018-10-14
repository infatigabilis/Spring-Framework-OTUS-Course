package ru.otus.springframework.hw19.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("login")
public class AuthController {
    public static final String LOGIN_PAGE = "login/index";
    public static final String SUCCESS_PAGE = "login/success";
    private static final String FAILURE_PAGE = "login/failure";

    @GetMapping
    public String loginPage() {
        return LOGIN_PAGE;
    }

    @GetMapping(params = "failure")
    public String loginSuccessPage() {
        return FAILURE_PAGE;
    }

    @GetMapping("success")
    public String loginFailurePage(Principal principal) {
        return SUCCESS_PAGE;
    }
}
