package com.example.project_management_system.controller;

import com.example.project_management_system.Project;
import com.example.project_management_system.dto.CreateProjectRequestDto;
import com.example.project_management_system.dto.ProjectDto;
import com.example.project_management_system.dto.TaskDto;
import com.example.project_management_system.dto.UserDto;
import com.example.project_management_system.mapper.ProjectMapper;
import com.example.project_management_system.service.ProjectService;
import com.example.project_management_system.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectController(ProjectService projectService, TaskService taskService, ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.projectMapper = projectMapper;
    }
    @GetMapping
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        try{
            ProjectDto projectDto = projectService.getProjectById(id);
            return ResponseEntity.ok(projectDto);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }

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

    //nie wiem co z tym
    //czy to tez walidatorem zrobic przez requesty bo torche nie ma to sensu robic kolejne klasy
    //nie bede sie super drobnil
    //bo user jest juz stworzony nie muze dawac walidatora na kreacje go jednye czeog potrzebuje to jego id
    //a skoro istnieje to musi miec id , moge podac tylko zle id niestiejacego uzytkownika
    //tak samo z projektem narazie skip
    @PostMapping("/{projectId}/members/{userId}")
    public ResponseEntity<Project> addMember(@PathVariable Long projectId, @PathVariable Long userId) {
        try{
            Project project = projectService.addMemberToProject(projectId , userId);
            return ResponseEntity.ok(project);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping
//    public ResponseEntity<Project> addProject(@RequestBody Project project){
//        Project createdProject = projectService.createProject(project);
//        return new ResponseEntity<>(createdProject , HttpStatus.CREATED);
//    }
    //wersja z validatorem
    @PostMapping
    public ResponseEntity<?> createProject(@Valid @RequestBody CreateProjectRequestDto requestDto) {
        try{
            Project project = projectMapper.toEntity(requestDto);
            Project createdProject = projectService.createProject(project);
            return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }


    }
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id , @RequestBody Project project) {
        try{
            Project updatedProject = projectService.updateProject(id, project);
            return ResponseEntity.ok(updatedProject);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
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
