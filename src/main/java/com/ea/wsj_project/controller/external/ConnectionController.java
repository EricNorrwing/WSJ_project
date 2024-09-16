package com.ea.wsj_project.controller.external;

import com.ea.wsj_project.model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ConnectionController {
    //TODO Consume API
    @GetMapping("https://api.themoviedb.org/3/movie/646097?api_key=52f1f1bb7e285951c81a84abcd0cabf7")
    public String getMovieByID (@RequestParam Long id) {
        return null;
    }

}
