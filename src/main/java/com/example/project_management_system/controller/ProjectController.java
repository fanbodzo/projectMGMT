package com.example.project_management_system.controller;

import com.example.project_management_system.Project;
import com.example.project_management_system.dto.ProjectDto;
import com.example.project_management_system.dto.TaskDto;
import com.example.project_management_system.dto.UserDto;
import com.example.project_management_system.service.ProjectService;
import com.example.project_management_system.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final TaskService taskService;

    @Autowired
    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
    @GetMapping("/{id}")
    public ProjectDto getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }
    @GetMapping("/{projectId}/members")
    public ResponseEntity<Set<UserDto>> getMembers(@PathVariable Long projectId) {
        try{
            Set<UserDto> members = projectService.listAllMembersOfProject(projectId);
            return ResponseEntity.ok(members);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<TaskDto>> getTasks(@PathVariable Long projectId) {
        try{
            List<TaskDto> tasks = taskService.getAllTasksByProjectId(projectId);
            return ResponseEntity.ok(tasks);
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }




    @PostMapping("/{projectId}/members/{userId}")
    public ResponseEntity<Project> addMember(@PathVariable Long projectId, @PathVariable Long userId) {
        try{
            Project project = projectService.addMemberToProject(projectId , userId);
            return ResponseEntity.ok(project);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Project addProject(@RequestBody Project project){
        return projectService.createProject(project);
    }
    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id , @RequestBody Project project) {
        return projectService.updateProject(id, project);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
    @DeleteMapping("/{projectId}/members/{userId}")
    public ResponseEntity<Project> removeMember(@PathVariable Long projectId, @PathVariable Long userId) {
        try{
            Project project = projectService.removeMemberFromProject(projectId, userId);
            return ResponseEntity.ok(project);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
