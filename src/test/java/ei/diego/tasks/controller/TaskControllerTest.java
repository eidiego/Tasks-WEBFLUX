package ei.diego.tasks.controller;

import ei.diego.tasks.controller.converter.TaskDTOConverter;
import ei.diego.tasks.controller.dto.TaskDTO;
import ei.diego.tasks.model.Task;
import ei.diego.tasks.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskControllerTest {

    @InjectMocks
    private TaskController controller;

    @Mock
    private TaskService service;

    @Mock
    private TaskDTOConverter converter;

    @Test
    public void controller_mustReturnOk_whenSaveSucessfully() {

        when(service.insert(any()))
                .thenReturn(Mono.just(new Task()));
        when(converter.convert(any(Task.class)))
                .thenReturn(new TaskDTO());

        WebTestClient client = WebTestClient.bindToController(controller).build();

        client.post()
                .uri("/task")
                .bodyValue((new TaskDTO()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskDTO.class);

    }


}
