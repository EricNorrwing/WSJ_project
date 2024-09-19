package com.ea.wsj_project.service;

import com.ea.wsj_project.model.movie.Movie;
import com.ea.wsj_project.model.movie.MovieEntity;

import java.util.Optional;

public interface MovieService {

    public Optional<Movie> getMovieById (Long id);
    public Optional<Movie> getMovieByUsername (String name);
    public Optional<MovieEntity> createMovie(MovieEntity movie);
    public Optional<MovieEntity> updateMovie(MovieEntity movie);
    public Optional<MovieEntity> deleteMovie(Long id);
    public MovieEntity saveMovie(MovieEntity movie);

}
