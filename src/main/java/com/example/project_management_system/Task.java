package com.example.project_management_system;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "task" )
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status = "TO_DO";

    @Column(name = "priority")
    private String priority = "MEDIUM";

    @Column(name = "createdAt" , updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    //metody na czas
    @PrePersist // Metoda wywoła się tuż przed pierwszym zapisem do bazy
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate // Metoda wywoła się tuż przed aktualizacją
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    //klucze obcje i relacje  , klucz obcy ma _ w sobie

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id" , nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="assignee_id")
    private User assignees;

    @OneToMany(mappedBy = "task" ,cascade = CascadeType.ALL , orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

}
