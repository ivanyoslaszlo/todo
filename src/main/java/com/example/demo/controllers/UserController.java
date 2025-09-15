package com.example.demo.controllers;

import com.example.demo.entities.Users;
import com.example.demo.respository.Repository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class UserController {

    private final Repository userRepository;

    public UserController(Repository userRepository) {
        this.userRepository = userRepository;
    }



    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(HttpSession session) {

        String username = (String) session.getAttribute("user");
        if (username==null){
            return ResponseEntity.status(401).body("Be kell jelentkezni admin fiókal!");
        }

        if (username.equals("user")){
            return ResponseEntity.status(403).body("Nincs jogosultságod!");
        }else
        {
            List <Users> users=userRepository.findAllUsers();
            return ResponseEntity.ok(users);
        }

    }
}
