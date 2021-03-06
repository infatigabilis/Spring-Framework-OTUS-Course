package ru.otus.springframework.hw31.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("login")
public class AuthController {
    private static final String LOGIN_PAGE = "login/index";
    private static final String SUCCESS_PAGE = "login/success";
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
