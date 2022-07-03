package com.plats.allinoutservice.accountservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping(value = {"/login", "/signup"})
    public String login_get() {
        return "login.html";
    }

}
