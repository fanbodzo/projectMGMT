package com.example.project_management_system.repository;

import com.example.project_management_system.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    //po dodaniu JPaspecification executor moge usunac te metody ktore nie sluza do dynamicznmego wyszukiwania
    Optional<Task> findByTitle(String title);
    List<Task> findByStatus(String status);
    List<Task> findByPriority(String priority);
    boolean existsById(Long id);
    boolean existsByTitle(String title);
    List<Task> findByAssigneesId(Long userId);
    List<Task> findByProjectId(Long projectId);
    List<Task> findByProjectIdAndAssigneesId(Long projectId, Long userId);
    List<Task> findByStatusAndAssigneesId(String status, Long userId);

}
