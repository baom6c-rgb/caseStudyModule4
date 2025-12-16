package com.codegym.moviebackendjwt.controller;

import com.codegym.moviebackendjwt.model.Users;
import com.codegym.moviebackendjwt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserAdminController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }
}
