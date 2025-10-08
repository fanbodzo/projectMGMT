package com.example.project_management_system.service;

import com.example.project_management_system.Project;
import com.example.project_management_system.User;
import com.example.project_management_system.dto.ProjectDto;
import com.example.project_management_system.dto.UserDto;
import com.example.project_management_system.mapper.ProjectMapper;
import com.example.project_management_system.mapper.UserMapper;
import com.example.project_management_system.repository.ProjectRepository;
import com.example.project_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserService userService, UserRepository userRepository
            , UserMapper userMapper , ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectMapper = projectMapper;
    }

    public List<ProjectDto> getAllProjects() {
        return projectMapper.toDtoList(projectRepository.findAll());
    }

    //bardzo niebezpieczne .get() bo jak bedzie pusta wartsoc to rzuci wyjatkiem
    // i leipiej tak nierobic albo dac obsluge wyjatku
    public ProjectDto getProjectById(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Project not found"));
        return projectMapper.toDto(project);
    }

    public Project createProject(Project project){
        if(projectRepository.existsByProjectKey(project.getProjectKey())){
            throw new IllegalArgumentException("Project with this key" + project.getProjectKey() + " already exists");
        }
        //return null; nie rzucac null bo w api bedzie to jako OK i zaminilem koljencscia i usunalem ! z if

        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project project) {
        Optional<Project> projectToUpdate =  projectRepository.findById(id);

        if(projectToUpdate.isPresent()){
            Project existingProject = projectToUpdate.get();
            existingProject.setName(project.getName());
            existingProject.setDescription(project.getDescription());
            //nie aktualizuje sie klucza projektu bo to zla praktyka
            //existingProject.setKey(project.getKey());
            return projectRepository.save(existingProject);
        }
        throw new IllegalArgumentException("Project not found");
    }
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
    //dzialanie na realcjimany ot many
    // na tabeli posredniczacej i robimy to w projectService bo w
    // nim opsiywalismy ta relacje many to many
    //poporstu edytujemy tutaj Set members ktory mamy zdefionowany w polaczeniach
    //easy
    public Project addMemberToProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        project.getMembers().add(user);

        return projectRepository.save(project);
    }

    public Project removeMemberFromProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        project.getMembers().remove(user);

        return projectRepository.save(project);
    }
    public Set<UserDto> listAllMembersOfProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        return userMapper.toDtoSet(project.getMembers());
    }

}
