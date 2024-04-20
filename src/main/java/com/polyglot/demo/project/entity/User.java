package com.polyglot.demo.project.entity;

import java.util.UUID;

public class User {
    private UUID id;
    private String email;
    private String password;
    private String hashedPassword;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.id = new UUID(10, 8);
    }

    public UUID getId() {
        return id;
    }
   
    public String getEmail() {
        return email;
    }
   
    public String getPassword() {
        return password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String value) {
        hashedPassword = value;
    }
  
 
}
