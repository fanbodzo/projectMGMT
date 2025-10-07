package com.example.project_management_system.repository.specification;

import com.example.project_management_system.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {
    //sepcyfikacja ktora nic nie robi  element neutralny
    public static Specification<Task> trueCondition() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    //filtorwanie po statusie , raczej klocek
    public static Specification<Task> hasStatus(String status) {
        return(root, query, criteriaBuilder) -> {
            //root to encja task
            //criteria builder to fabryka warunkow
            //tworzy warunek root.get("status") == status
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }
    public static Specification<Task> hasAssigneeId(Long assigneeId) {
        return(root, query, criteriaBuilder) -> {
            //.get("assignee") to dostep do pola relacyjnego
            //a drugi get to dostep do id w tym polu
            return criteriaBuilder.equal(root.get("assignee").get("id"), assigneeId);
        };
    }
}
