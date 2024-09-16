package com.ea.wsj_project.controller.service;

import com.ea.wsj_project.model.User;
import org.springframework.stereotype.Service;


public interface UserService {

    public User getUser (Long Id);
    public User createUser(User user);
    public User updateUser(User user);
    public User deleteUser(Long Id);

}
