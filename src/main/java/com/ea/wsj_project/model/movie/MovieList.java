package com.ea.wsj_project.model.movie;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieList {
    private int page;
    private List<Movie> results;
    private int total_results;
    private int total_pages;

}