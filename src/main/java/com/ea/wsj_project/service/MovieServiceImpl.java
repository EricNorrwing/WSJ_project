package com.ea.wsj_project.service;

import com.ea.wsj_project.model.User;
import com.ea.wsj_project.model.movie.Movie;
import com.ea.wsj_project.model.movie.MovieEntity;
import com.ea.wsj_project.model.movie.MovieList;
import com.ea.wsj_project.repository.MovieRepository;
import com.ea.wsj_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, UserRepository userRepository, UserService userService) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public Optional<MovieEntity> deleteMovie(Long id) {
        Optional<MovieEntity> movieEntity = movieRepository.findById(id);

        if (movieEntity.isPresent()) {
            MovieEntity movie = movieEntity.get();

            if (movie.getUser() != null) {
                User user = movie.getUser();
                user.getWatchlist().remove(movie);
            }

            movieRepository.delete(movie);

            return Optional.of(movie);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MovieEntity> saveMovieForUser(int listNumber, Long userId, MovieList movieList) {
        if (movieList == null || movieList.getResults() == null || movieList.getResults().isEmpty()) {
            return Optional.empty();
        }

        if (listNumber < 0 || listNumber >= movieList.getResults().size()) {
            return Optional.empty();
        }

        Movie movie = movieList.getResults().get(listNumber);

        MovieEntity movieEntity = convertToMovieEntity(movie);

        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        if (user.getWatchlist().stream().anyMatch(m -> m.getTitle().equals(movieEntity.getTitle()))) {
            return Optional.empty();
        }

        movieEntity.setUser(user);

        return Optional.of(saveMovie(movieEntity));
    }

    @Override
    public MovieEntity saveMovie(MovieEntity movie) {
        return movieRepository.save(movie);
    }

    @Override
    public MovieEntity convertToMovieEntity(Movie movie) {
        MovieEntity movieEntity = new MovieEntity();

        movieEntity.setTitle(movie.getTitle());
        movieEntity.setAdult(movie.isAdult());
        movieEntity.setBackdropPath(movie.getBackdropPath());
        movieEntity.setBudget(movie.getBudget());
        movieEntity.setImdbId(movie.getImdbId());
        movieEntity.setOriginalLanguage(movie.getOriginalLanguage());
        movieEntity.setOriginalTitle(movie.getOriginalTitle());
        movieEntity.setPopularity(movie.getPopularity());
        movieEntity.setPosterPath(movie.getPosterPath());
        movieEntity.setReleaseDate(movie.getReleaseDate());
        movieEntity.setRevenue(movie.getRevenue());
        movieEntity.setRuntime(movie.getRuntime());
        movieEntity.setStatus(movie.getStatus());
        movieEntity.setTagline(movie.getTagline());
        movieEntity.setVoteAverage(movie.getVoteAverage());
        movieEntity.setVoteCount(movie.getVoteCount());
        return movieEntity;
    }

    public Optional<MovieEntity> updateWatchedStatus(Long userId, Long movieId, boolean watched) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<MovieEntity> watchlist = user.getWatchlist();

            for (MovieEntity movie : watchlist) {
                if (movie.getMovieId().equals(movieId)) {
                    movie.setWatched(watched);

                    saveMovie(movie);

                    return Optional.of(movie);
                }
            }
        }
        return Optional.empty();
    }
}
