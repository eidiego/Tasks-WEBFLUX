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
import org.springframework.data.domain.Page;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.awt.print.Pageable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

    @Test
    public void controller_mustReturnOk_whenGetPageSucessfully() {

        when(service.findPaginated(any(), anyInt(), anyInt()))
                .thenReturn(Page.empty());

        WebTestClient client = WebTestClient.bindToController(controller).build();

        client.get()
                .uri("/task")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskDTO.class);

    }

    @Test
    public void controller_mustReturnNoContent_whenDeleteSucessfuly() {

        String taskId = "MockID";

        when(service.deleteById(Mockito.anyString()))
                .thenReturn(Mono.empty());

        WebTestClient client = WebTestClient.bindToController(controller).build();

        client.delete()
                .uri("/task/" + taskId)
                .exchange()
                .expectStatus().isNoContent();

    }


}
