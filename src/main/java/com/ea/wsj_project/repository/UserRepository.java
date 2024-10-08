package com.ea.wsj_project.repository;


import com.ea.wsj_project.model.User;
import com.ea.wsj_project.model.movie.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    ArrayList<MovieEntity> findWatchlistByUserId(Long userId);
}
