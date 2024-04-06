package ei.diego.tasks.controller;

import ei.diego.tasks.controller.converter.TaskDTOConverter;
import ei.diego.tasks.controller.dto.TaskDTO;
import ei.diego.tasks.model.Task;
import ei.diego.tasks.service.TaskService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService service;

    private final TaskDTOConverter converter;

    public TaskController(TaskService service, TaskDTOConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping
    public Mono<List<TaskDTO>> getTasks() {
        return service.list()
                .map(converter::convertList);
    }

    @PostMapping
    public Mono<TaskDTO> createTask(@RequestBody Task task){
        return service.insert(task)
                .map(converter::convert);
    }

}


