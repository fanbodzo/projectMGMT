package com.example.project_management_system.controller;

import com.example.project_management_system.Comment;
import com.example.project_management_system.dto.CommentDto;
import com.example.project_management_system.dto.CreateCommentRequestDto;
import com.example.project_management_system.mapper.CommentMapper;
import com.example.project_management_system.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentMapper commentMapper;
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<List<CommentDto>> getCommentsForTask(@PathVariable Long taskId) {
        try{
            List<CommentDto> comments = commentService.getCommentsForTask(taskId);
            return ResponseEntity.ok(comments);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsForUser(@PathVariable Long userId) {
        try{
            List<CommentDto> comments = commentService.getCommentsForUser(userId);
            return ResponseEntity.ok(comments);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

//
//    @PostMapping
//    public ResponseEntity<Comment> createComment(@RequestBody Comment comment,
//                                        @RequestParam Long userId,
//                                        @RequestParam Long taskId) {
//        Comment createdComment = commentService.createComment(comment, userId, taskId);
//        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
//    }

    //nowa metoda z walidacja
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CreateCommentRequestDto requestDto){
        CommentDto comment = commentService.createComment(requestDto);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }
}
