package com.example.lab1_servlet.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Login is required")
    private String login;
    @NotNull(message = "Password is required")
    private String password;

    private Boolean isBlacklist;
}
