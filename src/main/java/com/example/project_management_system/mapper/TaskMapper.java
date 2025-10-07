package com.example.project_management_system.mapper;

import com.example.project_management_system.Task;
import com.example.project_management_system.dto.TaskDto;
import com.example.project_management_system.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public TaskDto toDto(Task task) {
        if(task==null){return null;}
        TaskDto taskDto = new TaskDto();

        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setPriority(task.getPriority());
        taskDto.setUpdated_at(task.getUpdatedAt());
        taskDto.setCreated_at(task.getCreatedAt());
        if(task.getProject()!=null){
            taskDto.setProjectId(task.getProject().getId());
        }
        if(task.getAssignees()!=null){
            taskDto.setAssigneeId(taskDto.getAssigneeId());
        }

        return taskDto;
    }
    public List<TaskDto> toDtoList(List<Task> tasks) {
        if(tasks==null){return null;}
        return tasks.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
