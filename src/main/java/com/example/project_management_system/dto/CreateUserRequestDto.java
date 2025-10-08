package com.example.project_management_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequestDto {

    @NotBlank(message="Username can't be empty")
    @Size(min = 3, max = 20 , message = "Username must be at least 3 letters long to 20 max")
    private String username;

    @NotBlank(message="Password can't be empty")
    @Size(min = 8, message = "Password must be at least 8 letters long")
    private String password;

    @NotBlank(message="Email can't be empty")
    @Email(message = "Unvalid format")
    private String email;

    private String firstName;
    private String lastName;
}
