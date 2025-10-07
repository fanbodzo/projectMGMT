package com.example.project_management_system;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "projectName", nullable = false)
    private String name;

    @Column(name = "projectKey" , nullable = false , length = 10)
    private String projectKey;

    @Column(name = "description")
    private String description;

    //klucze obce i relacje

    @OneToMany(mappedBy = "project" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Task> tasks;

    //tabela posredniczaca
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="project_members",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    //set zamioast listy bo set nie przyjmuje duplikatow
    private Set<User> members = new HashSet<>();
}
