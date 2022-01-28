package y.w.api.springwebfluxdockerapi;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import y.w.api.springwebfluxdockerapi.GreetingService.GreetingResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SpringWebfluxDockerApiApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testHello() {
        webTestClient
            .get().uri("/hello")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(GreetingResponse.class).value( greeting -> {
                assertThat(greeting.getMessage().endsWith("world"));
            });

        webTestClient
            .get().uri("/")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound();
    }

}
