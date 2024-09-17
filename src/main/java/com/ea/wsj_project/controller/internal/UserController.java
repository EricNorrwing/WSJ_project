package com.ea.wsj_project.controller.internal;

import com.ea.wsj_project.response.ErrorResponse;
import com.ea.wsj_project.response.Response;
import com.ea.wsj_project.service.UserServiceImpl;
import com.ea.wsj_project.model.User;
import jakarta.validation.Valid;
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
    public ResponseEntity<Response> getUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);

        return userOptional.<ResponseEntity<Response>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity
                .status(404)
                .body(new ErrorResponse("No user exists wih id: " + id)));
    }

    @PostMapping("/newUser")
    public ResponseEntity<Response> newUser(@Valid @RequestBody User user) {
        Optional<User> createdUser = userService.createUser(user);

        return createdUser.<ResponseEntity<Response>>map(userObj -> ResponseEntity.status(201).body(userObj))
                .orElseGet(() -> ResponseEntity
                        .status(409)
                        .body(new ErrorResponse("Could not create new user") {
                        }));

    }


}
