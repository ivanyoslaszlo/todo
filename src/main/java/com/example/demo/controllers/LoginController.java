package com.example.demo.controllers;

import com.example.demo.entities.Users;
import com.example.demo.respository.Repository;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@Controller
public class LoginController {

    private final UserService userService;
    public LoginController(UserService userService ) {
        this.userService =  userService;
    }


    @PostMapping("/login")
    public String logincontroller(@RequestParam String username, @RequestParam String password, HttpSession session, Model modell) throws FileNotFoundException {

        String result =userService.loginUser(username,password,session);

        if (result.equalsIgnoreCase("User")){
            return "todo";

        }
        else if (result.equalsIgnoreCase("Admin")){
            return "AdminCenter";
        }
        else{
            modell.addAttribute("errorMessage",result);
            return "loginpage";
        }
    }
}
