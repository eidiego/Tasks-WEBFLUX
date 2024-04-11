package ei.diego.tasks.controller;

import ei.diego.tasks.controller.converter.TaskDTOConverter;
import ei.diego.tasks.controller.dto.TaskDTO;
import ei.diego.tasks.model.Task;
import ei.diego.tasks.model.TaskState;
import ei.diego.tasks.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


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
    public Page<TaskDTO> getTasks(@RequestParam(required = false) String id,
                                   @RequestParam(required = false) String title,
                                   @RequestParam(required = false) String description,
                                   @RequestParam(required = false, defaultValue = "0") int priority,
                                   @RequestParam(required = false) TaskState taskState,
                                   @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return service.findPaginated(converter.convert(id, title,description, priority, taskState), pageNumber, pageSize)
                .map(converter::convert);
    }

    @PostMapping
    public Mono<TaskDTO> createTask(@RequestBody TaskDTO taskDTO){
        return service.insert(converter.convert(taskDTO))
                .map(converter::convert);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable String id){
        return Mono.just(id)
                .flatMap(service::deleteById);
    }

}


