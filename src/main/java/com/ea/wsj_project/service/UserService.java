package com.ea.wsj_project.service;

import com.ea.wsj_project.model.User;
import com.ea.wsj_project.model.movie.MovieEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserService {

    public Optional<User> getUserById (Long id);
    public Optional<User> getUserByUsername (String name);
    public Optional<User> createUser(User user);
    public Optional<User> updateUser(User user);
    public Optional<User> deleteUser(Long id);
    public Optional<List<MovieEntity>> getWatchlist(Long id);

}
