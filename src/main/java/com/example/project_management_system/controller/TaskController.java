package com.example.project_management_system.controller;

import com.example.project_management_system.Comment;
import com.example.project_management_system.Task;
import com.example.project_management_system.dto.CommentDto;
import com.example.project_management_system.dto.TaskDto;
import com.example.project_management_system.service.CommentService;
import com.example.project_management_system.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final CommentService commentService;

    @Autowired
    public TaskController(TaskService taskService, CommentService commentService) {
        this.taskService = taskService;
        this.commentService = commentService;
    }
    //ulepszony http get z filtrem na id przypisanego uzytkownika lub status zadania
    //TODO zrobic cos takiego podobnego dla innych rzeczy jak komentarze
    // dla kazdego uzytkonika np komentarz przez uzytkownika wyszukany
    // uzytkownikow dla kazdego zadania i komentarz tez mozna
    @GetMapping
    public List<TaskDto> getAllTasksOrSearchTask(@RequestParam(required = false) String status
            ,@RequestParam(required = false) Long assigneeId) {
        return taskService.searchTask(status,assigneeId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        try{
            TaskDto taskDto = taskService.getTaskById(id);
            return ResponseEntity.ok(taskDto);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getTaskComments(@PathVariable Long id) {
        try{
            List<CommentDto> comments = commentService.getCommentsForTask(id);
            return ResponseEntity.ok(comments);
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try{
            Task updatedTask = taskService.updateTask(id, task);
            return ResponseEntity.ok(updatedTask);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskDto> deleteTask(@PathVariable Long id) {
        try{
            taskService.deleteTaskById(id);
            return ResponseEntity.ok().build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    //to byla moja wersja free style
//    @PostMapping("/{projectId}/{assigneeId}")
//    public void createTask(@PathVariable Long projectId , @PathVariable Long assigneeId , @RequestBody Task task) {
//        taskService.createTask(task, projectId, assigneeId);
//    }
    //wersja poprawna
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task,
                                     @RequestParam Long projectId,
                                     @RequestParam(required = false) Long assigneeId) {
        //tutaj dzieje sie cos innego niz zawsze odwolyniee sie do serwisu
        Task createdTask = taskService.createTask(task, projectId, assigneeId);
        // ResponseEntity sprawia ze mozemy zwaracas sobie sttus w postmanie przez json
        //dopkladne wyjasnieni zrobie sobie w notatkach w obisdianeie jak bd tluamczyc prjekt
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }


}
