package com.example.resttutorial.Components;

import com.example.resttutorial.Entities.Role;

public class UserPrint {
    private String username;
    private ERole role;

    public UserPrint(String username, ERole role){
        this.username = username;
        this.role = role;
    }

    public UserPrint(){}

    public String getUsername(){
        return username;
    }

    public ERole getRole(){
        return role;
    }
}
