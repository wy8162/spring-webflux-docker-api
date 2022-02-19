package y.w.api.springwebfluxdockerapi;

import static org.assertj.core.api.Assertions.assertThat;
import static y.w.api.springwebfluxdockerapi.HealthChecker.healthCheck;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Exchange = retrieve + additional information like http status.
 *
 */
@Slf4j
@ExtendWith(SpringExtension.class)
public class WebClientPathVariableQueryParameterTest {
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
        if (!healthCheck()) return;

// Method 1;
//        URI uri = UriComponentsBuilder
//            .fromUriString("http://localhost:8080/some/{pv}/others?id={qv}")
//            .build("pathVariable", "queryVar");

/// Method 2
//        URI uri = UriComponentsBuilder
//            .fromUriString("http://localhost:8080/some/{pv}/others")
//            .query("id={qv}")
//            .build("pathVariable", "queryVar");

        URI uri = UriComponentsBuilder
            .newInstance()
            .scheme("http")
            .host("localhost")
            .port(8080)
            .path("/api/some/{pv}/others")
            .query("id={qv}")
            .build("pathVariable", "queryVar");

        Mono<String> responseMono = webClient
            .get()
            .uri(uri)
            .accept(MediaType.APPLICATION_JSON)
            .exchangeToMono(response -> {
                if (response.statusCode().equals(HttpStatus.OK))
                    return response.bodyToMono(String.class);
                else if (response.statusCode().is4xxClientError()){
                    return Mono.just("Error response");
                } else {
                    return response.createException()
                        .flatMap(Mono::error);
                }
            });

        responseMono.as(StepVerifier::create)
            .assertNext(actual -> {
                assertThat(actual).contains("pathVariable");
                assertThat(actual).contains("queryVar");
                log.info(actual);
            })
            .verifyComplete();
    }
}
