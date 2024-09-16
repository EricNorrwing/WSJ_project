package com.ea.wsj_project.controller.service;

import com.ea.wsj_project.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService {

    public Optional<User> getUser (Long id);
    public Optional<User> createUser(User user);
    public Optional<User> updateUser(User user);
    public Optional<User> deleteUser(Long id);

}
