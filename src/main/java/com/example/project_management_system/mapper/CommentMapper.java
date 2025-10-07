package com.example.project_management_system.mapper;

import com.example.project_management_system.Comment;
import com.example.project_management_system.dto.CommentDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public CommentDto toDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCreated_at(comment.getCreatedAt());

        if(comment.getTask() != null) {
            commentDto.setTaskId(comment.getTask().getId());
        }
        if(comment.getAuthor() != null) {
            commentDto.setAuthorId(comment.getAuthor().getId());
        }

        return commentDto;
    }
    public List<CommentDto> toDtoList(List<Comment> comments) {
        return comments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
