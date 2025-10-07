package com.example.project_management_system.mapper;

import com.example.project_management_system.Task;
import com.example.project_management_system.dto.TaskDto;
import com.example.project_management_system.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
//ten mapper to mapper reczny bo moglem zrobic mniej jawnie na interfjsie
@Component
public class TaskMapper {
    private final UserMapper userMapper;

    @Autowired
    public TaskMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

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
        //wystarcyzlo wstrzyknac userMapper spoko mega latwe
        if(task.getAssignees()!=null){
            UserDto userDto = userMapper.toDto(task.getAssignees());
            taskDto.setAssignee(userDto);
        }

        return taskDto;
    }
    //konwersja listy taskow w zwyklym formacie
    //na liste taskow w formacie dto
    public List<TaskDto> toDtoList(List<Task> tasks) {
        if(tasks==null){return null;}
        return tasks.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
