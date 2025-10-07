package com.example.project_management_system;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content" , nullable = false)
    private String content;

    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist // Metoda wywoła się tuż przed pierwszym zapisem do bazy
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    //klucze obce
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id" , nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id" ,nullable = false)
    private User author;

}
