package com.example.project_management_system.service;

import com.example.project_management_system.Project;
import com.example.project_management_system.Task;
import com.example.project_management_system.User;
import com.example.project_management_system.dto.TaskDto;
import com.example.project_management_system.mapper.TaskMapper;
import com.example.project_management_system.mapper.UserMapper;
import com.example.project_management_system.repository.ProjectRepository;
import com.example.project_management_system.repository.TaskRepository;
import com.example.project_management_system.repository.UserRepository;
import com.example.project_management_system.repository.specification.TaskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository , ProjectRepository projectRepository, UserRepository userRepository, UserMapper userMapper, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.taskMapper = taskMapper;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
//    tak samo jak userService uze dto
//    public Task getTaskById(Long id) {
//        return taskRepository.findById(id).get();
//    }
// uzywam narazie dto tylko tutaj zeby wyjsc z petlii wysyania json
//    public TaskDto getTaskById(Long id) {
//        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
//
//        TaskDto taskDto = new TaskDto();
//        taskDto.setId(task.getId());
//        taskDto.setTitle(task.getTitle());
//        taskDto.setDescription(task.getDescription());
//        taskDto.setStatus(task.getStatus());
//        taskDto.setPriority(task.getPriority());
//        taskDto.setUpdated_at(task.getUpdatedAt());
//        taskDto.setCreated_at(task.getCreatedAt());
//        taskDto.setAssigneeId(taskDto.getAssigneeId());
//        taskDto.setProjectId(task.getProject().getId());
//
//
//        return taskDto;
//        //dobra wyjscie z petlii dziala super ale tearz musze zaimpleentowac reszte rzeczy i wroce do dto pozniej
//    }
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        return taskMapper.toDto(task);
    }


    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    public Task updateTask(Long id ,Task task) {
        Optional<Task> taskToUpdate = taskRepository.findById(id);

        if(taskToUpdate.isPresent()){
            Task existingTask = taskToUpdate.get();
            existingTask.setDescription(task.getDescription());
            existingTask.setPriority(task.getPriority());
            existingTask.setStatus(task.getStatus());
            return taskRepository.save(existingTask);
        }
        throw new IllegalArgumentException("Task not found");
    }

    //wykorzystywanie relacji w sql
    public Task createTask(Task task , Long projectId , Long assigneeId) {
        Project foundProject = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project not found"));
        task.setProject(foundProject);
        User foundUser = userRepository.findById(assigneeId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        task.setAssignees(foundUser);
        return taskRepository.save(task);
    }

    //uzywane w ProjectController zeby wsiwetlic wsyzksie taski dla danego projektu
    public List<TaskDto> getAllTasksByProjectId(Long projectId) {
        if(!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project not found");
        }
        List<Task> tasks = taskRepository.findByProjectId(projectId);

        return taskMapper.toDtoList(tasks);
    }

    //do filtorwania jest troche inne podejscie

    //to jest moja proba z stream ale nie ma sensu bo
    // pobieram z bazdy i bede fitrowac w pamieci apliakcji co jest nie wydaje wduzej bazie
    //nie obsluguje wyjatkow jak beidze cos null to jestem w dupie
    //dzieki temu pozalem zasade
    //filtorwac jak najblizej zrodla danych
    //bo to co robie jest na odwrot jestem daleko od bazy w aplikacji i prosez baze o wysaleni duzej ilsoci danych
    // nawet jezeli ogarene te null to i tak to jest mega niewydajne
    //bo przy duzej bazie jak bym mial 1mlnobiektow pobrac to gg wtedy
//    public List<TaskDto> searchTask (String status , Long assigneeId){
//        List<Task> tasks = taskRepository.findByAssigneeId(assigneeId);
//        return taskMapper.toDtoList(tasks.stream()
//                .filter(task -> task.getStatus().equals(status))
//                .collect(Collectors.toList()));
//
//    }
    //teraz poprawna metoda taka wydajniejsz z uzyciem og warunkow
    //proste podejscei bez JPA specifications
//    public List<TaskDto> searchTask (String status , Long assigneeId){
//        List<Task> tasks;
//
//        if(status != null & assigneeId != null) {
//            tasks = taskRepository.findByStatusAndAssigneesId(status , assigneeId);
//        }else if (status != null & assigneeId == null){
//            tasks = taskRepository.findByStatus(status);
//        }else if (status == null & assigneeId != null){
//            tasks = taskRepository.findByAssigneesId(assigneeId);
//        }else{
//            //jak nic nie znajdzie bo obydwa null to daje wszstko
//            //co chyba nie jest zbyt wydajne ale narazie moze byc.
//            tasks = taskRepository.findAll();
//        }
//
//        return taskMapper.toDtoList(tasks);
//    }
    //podejscie z jpa specifications
    public List<TaskDto> searchTask (String status , Long assigneeId){
        //zamiast Specyfication.where(null) ktoro ma zostac usuneite
        //pooprstu zaczynamy od pustej specyfikacji
        //mozna tez zrobic specyfikacje w locie ale wiecej roboty chyba i myslenia 
        Specification<Task> spec = TaskSpecification.trueCondition();

        if(status != null) {
            spec = spec.and(TaskSpecification.hasStatus(status));
        }
        if(assigneeId != null) {
            spec = spec.and(TaskSpecification.hasAssigneeId(assigneeId));
        }

        List<Task> tasks = taskRepository.findAll(spec);
        return taskMapper.toDtoList(tasks);
    }

}
