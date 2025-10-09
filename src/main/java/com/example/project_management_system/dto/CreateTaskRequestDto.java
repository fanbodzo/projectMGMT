package com.example.project_management_system.dto;

import com.example.project_management_system.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateTaskRequestDto {
    @NotBlank(message = "Title can't be empty")
    @Size(max = 50 , message = "max lenght is 50")
    private String title;

    @NotBlank(message = "Task must be in project")
    private Long projectId;

    private String description;
    private String status;
    private String priority;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private UserDto assignee;

}
