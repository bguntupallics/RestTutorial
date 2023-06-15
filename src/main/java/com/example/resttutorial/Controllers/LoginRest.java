package com.example.resttutorial.Controllers;

import com.example.resttutorial.Components.ERole;
import com.example.resttutorial.Entities.User;
import com.example.resttutorial.Exceptions.UserNotFoundException;
import com.example.resttutorial.Repositories.RoleRepository;
import com.example.resttutorial.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginRest {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public LoginRest(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/users")
    public List<String> allUsers(){
        List<User> users =  userRepository.findAll();
        ArrayList<String> usernames = new ArrayList<>();
        for (User user: users){
            usernames.add(user.getUsername() + " role: " + user.getRole());
        }
        return usernames;
    }

    @PostMapping("/validate")
    public boolean login(@RequestBody User user){
        try {
            User to_validate = userRepository.findByUsername(user.getUsername()).orElseThrow(UserNotFoundException::new);
            if (to_validate.getPassword().equals(user.getPassword())) {
                return true;
            } else {
                return false;
            }
        } catch (UserNotFoundException e){
            return false;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        User to_add = new User(user.getUsername(), user.getPassword(), ERole.ROLE_USER);
        userRepository.save(to_add);
        return ResponseEntity.ok(to_add);
    }
}
