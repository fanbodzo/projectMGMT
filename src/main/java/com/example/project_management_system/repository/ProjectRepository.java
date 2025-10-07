package com.example.project_management_system.repository;

import com.example.project_management_system.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    Optional<Project> findByName(String name);
    Optional<Project> findByProjectKey(String description);
    boolean existsByProjectKey(String key);
    boolean existsByName(String name);

}
