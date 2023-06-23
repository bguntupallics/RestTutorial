package com.example.resttutorial.Entities;

import com.example.resttutorial.Components.ERole;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    public User(String username, String password, ERole role){
        this.username = username;
        this.password = password;
        this.role = new Role(role);
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(ERole role){
        this.role = new Role(role);
    }

    public void setRole(Role role){
        this.role = role;
    }
}
