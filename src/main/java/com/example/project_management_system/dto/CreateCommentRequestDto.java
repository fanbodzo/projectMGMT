package com.example.project_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCommentRequestDto {

    @NotBlank(message = "Comment content can't be empty")
    private String content;
    //dalem nrazie long ale wyoadlao by dac obiekty jezlei mam robic walidacje czy sie zgadzaja
    //ale no narazie porzucam to ide na zajecia
    @NotBlank(message = "Comment must be divided into task")
    private Long task;

    @NotBlank(message = "Comment must be divided into user")
    private Long user;



}
