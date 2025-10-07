package com.example.project_management_system.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDto {
    //tutaj dajemy pooprstu to co chcemy wyswietlic to co nas interesuje
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    //to odziwo dziala
    private Long projectId;

    private UserDto assignee;


}
