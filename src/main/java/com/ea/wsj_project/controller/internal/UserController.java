package com.ea.wsj_project.controller.internal;

import com.ea.wsj_project.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final user

    @GetMapping("/user/{id}")
    public ResponseEntity<User, null> getUser(@PathVariable Integer id) {

    }

}
