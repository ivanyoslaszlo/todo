package com.example.demo.controllers;

import com.example.demo.entities.Users;

import org.springframework.web.bind.annotation.*;
import com.example.demo.UserService;


@RestController
public class RegisterController {


    private final UserService userService;
    public RegisterController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public String registerUser(@RequestBody Users user) {


        return userService.registerUser(user);
    }
}



