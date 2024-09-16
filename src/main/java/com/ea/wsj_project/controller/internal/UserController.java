package com.ea.wsj_project.controller.internal;

import com.ea.wsj_project.controller.service.UserServiceImpl;
import com.ea.wsj_project.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable Integer id) {

    }


}
