package com.example.project_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CreateProjectRequestDto {

    @NotBlank(message = "Project must be given a name")
    private String name;

    @NotBlank(message = "Project must have a key")
    @Size(max = 8 , message = "Maximal lenght of key is 8")
    private String projectKey;

    private String description;
    private List<TaskDto> tasks;
    private Set<UserDto> members;
}
