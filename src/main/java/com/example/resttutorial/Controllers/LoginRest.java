package com.example.resttutorial.Controllers;

import com.example.resttutorial.Components.ERole;
import com.example.resttutorial.Components.UserInfo;
import com.example.resttutorial.Components.UserPrint;
import com.example.resttutorial.Entities.Role;
import com.example.resttutorial.Entities.User;
import com.example.resttutorial.Exceptions.UserNotFoundException;
import com.example.resttutorial.Repositories.RoleRepository;
import com.example.resttutorial.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8000")
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
    public List<UserPrint> allUsers(){
        List<User> users =  userRepository.findAll();
        ArrayList<UserPrint> usernames = new ArrayList<>();
        for (User user: users){
            usernames.add(new UserPrint(user.getUsername(), user.getRole().getRole()));
        }
        return usernames;
    }

    @PostMapping("/validate")
    public boolean login(@RequestBody UserInfo user){
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
    public ResponseEntity<User> register(@RequestBody UserInfo user){
        User to_add = new User(user.getUsername(), user.getPassword());
        to_add.setRole(roleRepository.findByRole(ERole.ROLE_USER).orElseThrow(RuntimeException::new));
        userRepository.save(to_add);
        return ResponseEntity.ok(to_add);
    }

    @GetMapping("/roles")
    public List<Role> allRoles(){
        return roleRepository.findAll();
    }
}
