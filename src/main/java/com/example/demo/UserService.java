package com.example.demo;

import com.example.demo.entities.Users;
import com.example.demo.respository.Repository;

import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final Repository userRepository;

    public UserService(Repository userRepository){
        this.userRepository=userRepository;
    }


    public String registerUser(Users user){

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






}
