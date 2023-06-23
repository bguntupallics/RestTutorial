package com.example.resttutorial.Components;

public class UserInfo {

    private String username;
    private String password;

    public UserInfo(String username, String password){
        this.username = username;
        this.password = password;
    }

    public UserInfo(){}

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
