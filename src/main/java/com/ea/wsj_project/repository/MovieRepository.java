package com.ea.wsj_project.repository;


import com.ea.wsj_project.model.movie.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
}
