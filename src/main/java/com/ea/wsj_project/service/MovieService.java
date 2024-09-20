package com.ea.wsj_project.service;

import com.ea.wsj_project.model.movie.Movie;
import com.ea.wsj_project.model.movie.MovieEntity;
import com.ea.wsj_project.model.movie.MovieList;

import java.util.Optional;

public interface MovieService {

    public Optional<MovieEntity> deleteMovie(Long id);
    public Optional<MovieEntity> saveMovieForUser(int listNumber, Long userId, MovieList movieList);
    public MovieEntity saveMovie(MovieEntity movie);
    public MovieEntity convertToMovieEntity(Movie movie);
    public Optional<MovieEntity> updateWatchedStatus(Long userId, Long movieId, boolean watched);
}
