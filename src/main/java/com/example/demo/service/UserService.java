package com.example.demo.service;

import com.example.demo.entities.Users;
import com.example.demo.respository.Repository;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {


    private final Repository userRepository;

    public UserService(Repository userRepository) {
        this.userRepository = userRepository;
    }




    public String registerUser(Users user) {

        if (user.getUsername() == null || user.getUsername().trim().isEmpty() ||
                user.getEmail() == null || user.getEmail().trim().isEmpty() ||
                user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return "Hiba: minden mező kitöltése kötelező!";
        }


        if (userRepository.check_username(user.getUsername())) {
            return "Hiba foglalt felhasználó";
        }
        user.setPassword(userRepository.password_hash(user.getPassword()));
        userRepository.save(user);


        return "Sikeres regisztráció!: " + user.getUsername();
    }

    public String loginUser(String username, String password, HttpSession session) {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            return "Nincs ilyen felhasználó!";

        }
        if (!userRepository.check_password(password, user.getPassword())) {
            return "Hibás jelszó!";
        }
        session.setAttribute("user", user.getUsername());
        session.setAttribute("login_time", LocalDateTime.now().withNano(0));

        userRepository.updateLastLogin(user.getUsername());

        System.out.println("Bejelentkezett: " + user.getUsername() + " " + session.getAttribute("login_time"));

        if (userRepository.is_admin(user.getUsername())){
            return "Admin";
        }
        else{
            return "User";
        }


    }


}
