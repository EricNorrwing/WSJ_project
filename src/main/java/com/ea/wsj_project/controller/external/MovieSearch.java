package com.ea.wsj_project.controller.external;

import com.ea.wsj_project.model.movie.Movie;
import com.ea.wsj_project.model.movie.MovieList;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/api/search")
public class MovieSearch {

    private final WebClient webClient;

    // In-memory list of movies
    private MovieList movieList = new MovieList();

    public MovieSearch(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.themoviedb.org/3/")
                .build();
    }


    @GetMapping("/{query}")
    public Mono<MovieList> search(
            @PathVariable String query,
            @RequestParam(required = false) String releaseYear) {
        return this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("search/movie")
                        .queryParam("api_key", "52f1f1bb7e285951c81a84abcd0cabf7")
                        .queryParam("query", query)
                        .queryParamIfPresent("primary_release_year", Optional.ofNullable(releaseYear))
                        .build())
                .retrieve()
                .bodyToMono(MovieList.class)
                .doOnNext(fetchedMovies -> this.movieList = fetchedMovies);
    }


    @GetMapping("/save/{listNumber}")
    public Movie getNumber(@PathVariable int listNumber) {
        return movieList.getResults().get(listNumber);
    }

}
