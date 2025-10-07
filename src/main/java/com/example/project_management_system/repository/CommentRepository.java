package com.example.project_management_system.repository;

import com.example.project_management_system.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //UWAGA ZEBY NIE GUBIC POL TYPU ID BO SIE ZJEBALO MOCNO
    List<Comment> findByAuthorId(Long author);
    List<Comment> findByTaskId(Long taskId);

}
