package com.example.project_management_system.mapper;

import com.example.project_management_system.Project;
import com.example.project_management_system.dto.ProjectDto;
import com.example.project_management_system.dto.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;

    @Autowired
    public ProjectMapper(UserMapper userMapper, TaskMapper taskMapper) {
        this.userMapper = userMapper;
        this.taskMapper = taskMapper;
    }

    public ProjectDto toDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setDescription(project.getDescription());
        projectDto.setProjectKey(project.getProjectKey());
        //zeby to zrobic musz euzyc zgniezdzenia ale nie umiem jeszcze
//        projectDto.setTasks(project.getTasks());
        if(project.getTasks() != null) {
            projectDto.setTasks(taskMapper.toDtoList(project.getTasks()));
        }
        if(project.getMembers() != null) {
            projectDto.setMembers(userMapper.toDtoSet(project.getMembers()));
        }
        return projectDto;
    }
    public List<ProjectDto> toDtoList(List<Project> projects) {
        if(projects == null) {
            return null;
        }
        return projects.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
