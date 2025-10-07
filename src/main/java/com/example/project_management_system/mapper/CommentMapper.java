package com.example.project_management_system.mapper;

import com.example.project_management_system.Comment;
import com.example.project_management_system.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommentMapper {
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;

    @Autowired
    public CommentMapper(TaskMapper taskMapper, UserMapper userMapper) {
        this.taskMapper = taskMapper;
        this.userMapper = userMapper;
    }

    public CommentDto toDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCreated_at(comment.getCreatedAt());

        if(comment.getTask() != null) {
            commentDto.setTask(taskMapper.toDto(comment.getTask()));
        }
        if(comment.getAuthor() != null) {
            commentDto.setAuthor(userMapper.toDto(comment.getAuthor()));
        }

        return commentDto;
    }
    public List<CommentDto> toDtoList(List<Comment> comments) {
        return comments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
