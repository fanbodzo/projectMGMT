package com.example.project_management_system.controller;

import com.example.project_management_system.Comment;
import com.example.project_management_system.dto.CommentDto;
import com.example.project_management_system.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @GetMapping("/tasks/{taskId}")
    public List<CommentDto> getCommentsForTask(@PathVariable Long taskId) {
        return commentService.getCommentsForTask(taskId);
    }
    @GetMapping("/{userId}")
    public List<CommentDto> getCommentsForUser(@PathVariable Long userId) {
        return commentService.getCommentsForUser(userId);
    }
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment,
                                        @RequestParam Long userId,
                                        @RequestParam Long taskId) {
        Comment createdComment = commentService.createComment(comment, userId, taskId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
}
