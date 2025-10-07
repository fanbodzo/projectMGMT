package com.example.project_management_system.dto;

import com.example.project_management_system.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private String projectKey;
    //te pola narazie sa puste V ale wyswietla je wiec git
    //bede muisal zrobic zagniezdzenia ale najpierw posdtawy
    private List<TaskDto> tasks = new ArrayList<>();
    private List<User> members = new ArrayList<>();
}
