package com.example.project_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Long id;
    private String content;
    private LocalDateTime created_at;
    //to bedzie do zaimplementwoania narazei jest dziura w JSON przez to
    private UserDto author;
    private TaskDto task;

    private Long taskId;
    private Long authorId;

}
