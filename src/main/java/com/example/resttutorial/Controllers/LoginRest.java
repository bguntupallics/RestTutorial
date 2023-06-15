package com.example.resttutorial.Controllers;

import com.example.resttutorial.Entities.Employee;
import com.example.resttutorial.Entities.User;
import com.example.resttutorial.Exceptions.UserNotFoundException;
import com.example.resttutorial.Repositories.EmployeeRepository;
import com.example.resttutorial.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginRest {
    private final UserRepository repository;

    public LoginRest(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @GetMapping("/users")
    public List<String> allUsers(){
        List<User> users =  repository.findAll();
        ArrayList<String> usernames = new ArrayList<>();
        for (User user: users){
            usernames.add(user.getUsername());
        }
        return usernames;
    }

    @PostMapping("/validate")
    public String login(@RequestBody User user){
        try {
            User to_validate = repository.findByUsername(user.getUsername()).orElseThrow(UserNotFoundException::new);
            if (to_validate.getPassword().equals(user.getPassword())) {
                return "true";
            } else {
                return "false";
            }
        } catch (UserNotFoundException e){
            return "false";
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        User to_add = new User(user.getUsername(), user.getPassword());
        repository.save(to_add);
        return ResponseEntity.ok(to_add);
    }
}
