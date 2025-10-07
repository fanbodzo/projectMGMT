package com.example.project_management_system.service;

import com.example.project_management_system.Comment;
import com.example.project_management_system.Task;
import com.example.project_management_system.User;
import com.example.project_management_system.dto.CommentDto;
import com.example.project_management_system.mapper.CommentMapper;
import com.example.project_management_system.repository.CommentRepository;
import com.example.project_management_system.repository.TaskRepository;
import com.example.project_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    @Autowired
    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository, UserRepository userRepository , CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    public List<CommentDto> getCommentsForTask(Long taskId) {
        List<Comment> comments = commentRepository.findByTaskId(taskId);
        return commentMapper.toDtoList(comments);
    }

    public List<CommentDto> getCommentsForUser(Long authorId) {
        List<Comment> comments = commentRepository.findByAuthorId(authorId);
        return commentMapper.toDtoList(comments);
    }
    //moize byc przydatne
    //public List<Comment> getCommentsForUserAndTask(Long userId, Long taskId) {}

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public Comment createComment(Comment comment , Long taskId, Long authorId) {
        //zaimplementowac
        Task foundTask = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        comment.setTask(foundTask);
        User foundUser = userRepository.findById(authorId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        comment.setAuthor(foundUser);
        return commentRepository.save(comment);
    }

    //uzywane w TaskController zeby wsiwetlic wsyzksie komentarze dla danego taska
    public List<CommentDto> getAllCommentsForTask(Long taskId) {
        if(!taskRepository.existsById(taskId)) {
            throw new IllegalArgumentException("Task not found");
        }
        List<Comment> comments = commentRepository.findByTaskId(taskId);
        return commentMapper.toDtoList(comments);
    }
}
