package com.codegym.moviebackendjwt.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String greeting(HttpServletRequest request) {
        return "Welcome To Spring Security..." + request.getRequestedSessionId();
    }
}
