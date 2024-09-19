package com.ea.wsj_project.service;

import com.ea.wsj_project.model.movie.Movie;
import com.ea.wsj_project.model.movie.MovieEntity;
import com.ea.wsj_project.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Optional<Movie> getMovieById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Movie> getMovieByUsername(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<MovieEntity> createMovie(MovieEntity movie) {
        return Optional.empty();
    }

    @Override
    public Optional<MovieEntity> updateMovie(MovieEntity movie) {
        return Optional.empty();
    }

    @Override
    public Optional<MovieEntity> deleteMovie(Long id) {
        return Optional.empty();
    }


    @Override
    public MovieEntity saveMovie(MovieEntity movieEntity) {
        return movieRepository.save(movieEntity);
    }


}
