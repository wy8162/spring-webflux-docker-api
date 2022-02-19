package y.w.api.springwebfluxdockerapi;

import static org.assertj.core.api.Assertions.assertThat;
import static y.w.api.springwebfluxdockerapi.HealthChecker.healthCheck;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException.BadRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import y.w.api.springwebfluxdockerapi.general.MathDTO;

/**
 * Run the application to run the test.
 *
 */
@Slf4j
@ExtendWith(SpringExtension.class)
public class WebClientBadRequestTest {
    private WebClient webClient;

    @BeforeEach
    public void setUp() {
        webClient = WebClient
            .builder()
            .baseUrl("http://localhost:8080")
            .build();
    }

    @Test
    void testMathCompute() {
        if (!healthCheck()) return;

        MathDTO mathDTO = MathDTO.builder().firstNumber(100).secondNumber(10).operator("+").build();

        Mono<MathDTO> response = this.webClient
            .post()
            .bodyValue(mathDTO)
            .retrieve()
            .bodyToMono(MathDTO.class)
            .doOnError(err -> System.out.println(err.getMessage()))
            .doOnNext(System.out::println);

        StepVerifier.create(response)
            .verifyError(BadRequest.class);
    }

}
