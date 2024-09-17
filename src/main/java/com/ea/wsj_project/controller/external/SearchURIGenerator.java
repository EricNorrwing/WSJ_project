package com.ea.wsj_project.controller.external;

import io.netty.handler.ssl.SslClientHelloHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
public class SearchURIGenerator {


    @GetMapping("/search")
    public String search(
            @RequestParam String query,
            @RequestParam Optional<String> lang,
            @RequestParam Optional<String> releaseYear,
            @RequestParam Optional<String> region,
            @RequestParam Optional<String> year) {

        // Build the base URL
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("https://api.example.com/search")
                .queryParam("query", query); // Mandatory param

        // Add optional parameters if present
        lang.ifPresent(value -> builder.queryParam("lang", value));
        releaseYear.ifPresent(value -> builder.queryParam("releaseYear", value));
        region.ifPresent(value -> builder.queryParam("region", value));
        year.ifPresent(value -> builder.queryParam("year", value));

        return builder.toUriString();
    }
}
