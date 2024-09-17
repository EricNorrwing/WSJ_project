package com.ea.wsj_project.model.dto;

import com.ea.wsj_project.response.Response;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO implements Response {

    @NotBlank(message = "Username cannot be blank")
    @Email(message = "Username must be of type email")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 3, message = "Password must be at least 3 characters long")
    private String password;

    public UserDTO() {
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
