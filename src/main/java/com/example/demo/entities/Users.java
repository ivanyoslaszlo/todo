package com.example.demo.entities;



public class Users {

    private String username;
    private String password;
    private String email;
    private String role;
    private String registeredAt;
    private String lastLogin;


    public void setRegisteredAt(String registeredAt) {
        this.registeredAt = registeredAt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }









    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getRegisteredAt() {
        return registeredAt;
    }

    public String getLastLogin() {
        return lastLogin;
    }


}
