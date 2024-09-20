package com.ea.wsj_project.model;

import com.ea.wsj_project.model.movie.MovieEntity;
import com.ea.wsj_project.response.Response;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name="users")
@Getter
@Setter
public class User implements Response {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotBlank(message = "Username cannot be blank")
    @Email(message = "Username must be of type email")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 3, message = "Password must be at least 3 characters long")
    private String password;


    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<MovieEntity> watchlist;

}
