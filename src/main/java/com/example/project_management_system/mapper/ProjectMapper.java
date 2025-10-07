package com.example.project_management_system.mapper;

import com.example.project_management_system.Project;
import com.example.project_management_system.dto.ProjectDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public ProjectDto toDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setDescription(project.getDescription());
        projectDto.setProjectKey(project.getProjectKey());
        //zeby to zrobic musz euzyc zgniezdzenia ale nie umiem jeszcze
//        projectDto.setTasks(project.getTasks());
        return projectDto;
    }

}
