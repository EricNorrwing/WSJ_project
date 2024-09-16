package com.ea.wsj_project.controller.internal;

import com.ea.wsj_project.service.UserServiceImpl;
import com.ea.wsj_project.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity
                    .status(404)
                    .body("No user exists wih id: " + id);
        }
    }

    @PostMapping("/newUser")
    public ResponseEntity<?> newUser(@RequestBody User user) {
        Optional<User> createdUser = userService.createUser(user);

        if(createdUser.isPresent()) {
            return ResponseEntity.ok(createdUser.get());

        }
            return ResponseEntity.status(409)
                    .body("The user already exists with username: " + user.getUsername());

    }




}
