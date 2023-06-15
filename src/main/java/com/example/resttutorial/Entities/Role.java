package com.example.resttutorial.Entities;

import com.example.resttutorial.Components.ERole;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ERole role;

    @OneToMany(mappedBy = "role")
    private Set<User> users;

    public Role(){}

    public Role(ERole role){
        this.role = role;
    }

    public ERole getRole(){
        return role;
    }

    public void setRole(ERole role){
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString(){
        return role.toString();
    }
}
