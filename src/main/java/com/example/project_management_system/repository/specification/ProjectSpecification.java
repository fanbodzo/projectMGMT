package com.example.project_management_system.repository.specification;

import com.example.project_management_system.Project;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecification {

    public static Specification<Project> trueCondition(){
        return (root, query, criteriaBuilder)-> criteriaBuilder.conjunction();
    }
    //TODO
    // dodac wyszukiwanie po id uzytkonwnika , w jakich jest projektach
    //

}
