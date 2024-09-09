package com.ea.wsj_project.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

@Entity
@Table(name="movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    //Title, Description, Comments, Ratings, Genre
}
