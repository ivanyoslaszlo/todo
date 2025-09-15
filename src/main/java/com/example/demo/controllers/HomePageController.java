package com.example.demo.controllers;

import com.example.demo.respository.Repository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Controller
public class HomePageController {

    private final Repository userRepository;

    public HomePageController(Repository userRespository) {
        this.userRepository = userRespository;
    }


    @GetMapping("/")
    public String showLoginPage(HttpSession session) {
        session.invalidate();
        return "loginpage";
    }


    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @GetMapping("/todo")
    public String showTodoPage(HttpSession session, HttpServletResponse response) throws SQLException {

        String username = (String) session.getAttribute("user");


        if (username == null) {
            return "loginpage";
        }

        if (userRepository.is_admin(username)) {
            return "AdminCenter";
        } else if (!userRepository.is_admin(username)) {
            return "todo";
        } else {
            return "loginpage";
        }

    }

    @PostMapping("/kilepes")
    public String logout(HttpSession session) {

        LocalDateTime belepes_ideje = (LocalDateTime) session.getAttribute("login_time");
        LocalDateTime kilepes_ideje = LocalDateTime.now().withNano(0);
        Duration munkamenet = Duration.between(belepes_ideje, kilepes_ideje);
        long hour = munkamenet.toHours();
        long minutes = munkamenet.toMinutes() % 60;
        long seconds = munkamenet.toSeconds() % 60;

        System.out.println(session.getAttribute("user") + " Kilépett: " + kilepes_ideje + " || " + "Munkamenet hossza: " + hour + " óra " + minutes + " perc " + seconds + " másodperc");
        session.invalidate();
        return "redirect:/";
    }


}
