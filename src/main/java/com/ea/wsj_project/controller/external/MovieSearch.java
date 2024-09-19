package com.ea.wsj_project.controller.external;

import com.ea.wsj_project.model.User;
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

import java.util.Optional;

@RestController
@RequestMapping("/api/search")
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

    @GetMapping("/{query}")
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

        MovieEntity movieEntity = convertToMovieEntity(movie);

        MovieEntity savedMovie = movieService.saveMovie(movieEntity);

        return ResponseEntity.ok(movie);
    }

    private MovieEntity convertToMovieEntity(Movie movie) {
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

    @GetMapping("/save/{listNumber}/user/{userId}")
    public ResponseEntity<MovieEntity> saveMovieForUser(
            @PathVariable int listNumber,
            @PathVariable Long userId) {
        if (listNumber < 0 || listNumber >= movieList.getResults().size()) {
            return ResponseEntity.status(204).build();  // You can remove the ErrorResponse since we're returning MovieEntity now
        }

        System.out.println("hej");

        // Get the selected movie from the list
        Movie movie = movieList.getResults().get(listNumber);

        // Convert Movie to MovieEntity
        MovieEntity movieEntity = convertToMovieEntity(movie);

        // Find the user by userId
        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).build();  // If user not found, return 404
        }

        User user = userOptional.get();

        // Associate the movie with the user
        movieEntity.setUser(user);

        // Save the movieEntity to the database
        MovieEntity savedMovie = movieService.saveMovie(movieEntity);

        // Return the saved movie
        return ResponseEntity.ok(savedMovie);
    }


}