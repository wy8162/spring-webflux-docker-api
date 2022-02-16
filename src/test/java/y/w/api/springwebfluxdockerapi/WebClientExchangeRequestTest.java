package y.w.api.springwebfluxdockerapi;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import y.w.api.springwebfluxdockerapi.pojo.ApiResponse;

/**
 * Exchange = retrieve + additional information like http status.
 *
 */
@Slf4j
@ExtendWith(SpringExtension.class)
public class WebClientExchangeRequestTest {
    private WebClient webClient;

    @BeforeEach
    public void setUp() {
        webClient = WebClient
            .builder()
            .baseUrl("http://localhost:8080")
            .build();
    }

    @Test
    void testHelloCheckingStatus() {
        Mono<ApiResponse> responseMono = webClient
            .get()
            .uri("/hello-error")
            .accept(MediaType.APPLICATION_JSON)
            .exchangeToMono(response -> {
                if (response.statusCode().equals(HttpStatus.OK))
                    return response.bodyToMono(ApiResponse.class);
                else if (response.statusCode().is4xxClientError()){
                    return Mono.just(new ApiResponse(response.statusCode(), "Error response"));
                } else {
                    return response.createException()
                        .flatMap(Mono::error);
                }
            });

        responseMono.as(StepVerifier::create)
            .assertNext(actual -> {
                assertThat(actual.getMessage()).contains("Error response");
                log.info(actual.toString());
            })
            .verifyComplete();
    }
}
