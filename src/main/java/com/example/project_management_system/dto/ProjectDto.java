package com.example.project_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private List<TaskDto> tasks;
    //set bo nie chce duplikatow
    private Set<UserDto> members;
}
