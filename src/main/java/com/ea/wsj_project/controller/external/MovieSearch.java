package com.ea.wsj_project.controller.external;

import com.ea.wsj_project.model.movie.Movie;
import com.ea.wsj_project.model.movie.MovieEntity;
import com.ea.wsj_project.model.movie.MovieList;
import com.ea.wsj_project.response.ErrorResponse;
import com.ea.wsj_project.response.Response;
import com.ea.wsj_project.service.MovieService;
import com.ea.wsj_project.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MovieSearch {

    @Value("${tmdb_api_key}")
    private String apiKey;

    private final WebClient webClient;

    private final MovieService movieService;

    private MovieList movieList = new MovieList();

    private final UserService userService;

    public MovieSearch(WebClient.Builder webClientBuilder, MovieService movieService, UserService userService) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.themoviedb.org/3/")
                .build();
        this.movieService = movieService;
        this.userService = userService;
    }

    @GetMapping("/search/{query}")
    public Mono<MovieList> search(
            @PathVariable String query,
            @RequestParam(required = false) String releaseYear) {
        return this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("search/movie")
                        .queryParam("api_key", apiKey)
                        .queryParam("query", query)
                        .queryParamIfPresent("primary_release_year", Optional.ofNullable(releaseYear))
                        .build())
                .retrieve()
                .bodyToMono(MovieList.class)
                .doOnNext(fetchedMovies -> this.movieList = fetchedMovies);
    }

    @GetMapping("/save/{listNumber}")
    public ResponseEntity<Response> getNumber(@PathVariable int listNumber) {
        if (listNumber < 0 || listNumber >= movieList.getResults().size()) {
            return ResponseEntity.status(204).body(new ErrorResponse("Index out of bounds"));
        }
        Movie movie = movieList.getResults().get(listNumber);

        MovieEntity movieEntity = movieService.convertToMovieEntity(movie);

        MovieEntity savedMovie = movieService.saveMovie(movieEntity);

        return ResponseEntity.ok(movie);
    }

    // POST-MAPPING
    @GetMapping("/save/{listNumber}/user/{userId}")
    public ResponseEntity<?> saveMovieForUser(
            @PathVariable int listNumber,
            @PathVariable Long userId) {

        if (movieList == null || movieList.getResults().isEmpty()) {
            return ResponseEntity.status(400).body(new ErrorResponse("Movie list is empty or not initialized"));
        }

        if (listNumber < 0 || listNumber >= movieList.getResults().size()) {
            return ResponseEntity.status(400).body(new ErrorResponse("Invalid movie index"));
        }

        Optional<MovieEntity> savedMovie = movieService.saveMovieForUser(listNumber, userId, movieList);

        if (savedMovie.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponse("User or Movie not found"));
        }

        return ResponseEntity.ok(savedMovie.get());
    }

    @GetMapping("/{userId}/watchlist/")
    public ResponseEntity<?> getWatchlist(@PathVariable Long userId) {
        Optional<List<MovieEntity>> movieList = userService.getWatchlist(userId);

        if (movieList.isPresent()) {
            return ResponseEntity.ok(movieList.get());
        } else {
            return ResponseEntity.status(404).body(new ErrorResponse("Could not find user by this ID"));
        }
    }

    @PutMapping("/{userId}/watchlist/{movieId}")
    public ResponseEntity<?> updateWatchedStatus(
            @PathVariable Long userId,
            @PathVariable Long movieId,
            @RequestParam boolean watched) {

        Optional<MovieEntity> updatedMovie = movieService.updateWatchedStatus(userId, movieId, watched);

        if (updatedMovie.isPresent()) {
            return ResponseEntity.ok(updatedMovie.get());
        } else {
            return ResponseEntity.status(404).body(new ErrorResponse("Could not find user or movie by the provided ID"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        Optional<MovieEntity> deletedMovie = movieService.deleteMovie(id);

        if (deletedMovie.isPresent()) {
            return ResponseEntity.ok(deletedMovie.get());
        } else {
            return ResponseEntity.status(404).body(new ErrorResponse("Could not find movie by this ID"));
        }
    }

}
