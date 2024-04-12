package ei.diego.tasks.service;

import ei.diego.tasks.model.Task;
import ei.diego.tasks.repository.TaskCustomRepository;
import ei.diego.tasks.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final TaskCustomRepository taskCustomRepository;

    public TaskService(TaskRepository taskRepository, TaskCustomRepository taskCustomRepository) {
        this.taskRepository = taskRepository;
        this.taskCustomRepository = taskCustomRepository;
    }

    public Mono<Task> insert(Task task) {
        return Mono.just(task)
                .map(Task::insert)
                .flatMap(this::save);
    }

    public Page<Task> findPaginated(Task task, Integer pageNumber, Integer pageSize){
        return taskCustomRepository.findPaginated(task, pageNumber, pageSize);
    }

    public Mono<Task> save(Task task) {
        return Mono.just(task)
                .map(taskRepository::save);
    }

    public Mono<Void> deleteById(String id) {
        return Mono.fromRunnable(() -> taskRepository.deleteById(id));
    }

}
