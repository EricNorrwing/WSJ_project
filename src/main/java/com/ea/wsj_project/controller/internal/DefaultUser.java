package com.ea.wsj_project.controller.internal;

import com.ea.wsj_project.model.User;
import com.ea.wsj_project.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;

@Controller
public class DefaultUser {

    private final UserRepository userRepository;

    public DefaultUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostConstruct
    public void addDefaultUsers () {
        User user = new User ("EA", "any123");
        userRepository.save(user);
    }

}
