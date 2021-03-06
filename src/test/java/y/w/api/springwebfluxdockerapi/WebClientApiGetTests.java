package y.w.api.springwebfluxdockerapi;

import static org.assertj.core.api.Assertions.assertThat;
import static y.w.api.springwebfluxdockerapi.HealthChecker.healthCheck;

import java.net.HttpURLConnection;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.test.StepVerifier;
import y.w.api.springwebfluxdockerapi.pojo.WebFluxApiResponse;

/**
 * These tests use a real WebClient. So the application needs to be started
 * first.
 */
@Slf4j
@ExtendWith(SpringExtension.class)
public class WebClientApiGetTests {
    private WebClient webClient;

    @BeforeEach
    public void setUp() {
        HttpClient httpClient = HttpClient.create();
        webClient = WebClient
            .builder()
            .baseUrl("http://localhost:8080")
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

    @Test
    public void testHttpCaller() {
        try {
            URL url = new URL("http://localhost:8080/api/health");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int status = conn.getResponseCode();
        } catch (Exception e) {

        }

        //return status == HttpURLConnection.HTTP_OK;
    }

    @Test
    void testHello() {
        if (!healthCheck()) return;

        Mono<WebFluxApiResponse> responseMono = webClient
            .get()
            .uri("/api/hello")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(WebFluxApiResponse.class);

        responseMono.as(StepVerifier::create)
            .assertNext(actual -> {
                assertThat(actual.getMessage()).contains("Hello, World");
                log.info(actual.toString());
            })
            .verifyComplete();

        responseMono = webClient
            .get()
            .uri("/api/hello/{name}", "jack")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(WebFluxApiResponse.class);

        responseMono.as(StepVerifier::create)
            .assertNext(actual -> {
                assertThat(actual.getMessage()).contains("Hello, jack");
                log.info(actual.toString());
            })
            .verifyComplete();
    }

    @Test
    void testHelloCheckingStatus() {
        if (!healthCheck()) return;

        Mono<WebFluxApiResponse> responseMono = webClient
            .get()
            .uri("/api/hello-error")
            .accept(MediaType.APPLICATION_JSON)
            .exchangeToMono(response -> {
                if (response.statusCode().equals(HttpStatus.OK))
                    return response.bodyToMono(WebFluxApiResponse.class);
                else if (response.statusCode().is4xxClientError()){
                    return Mono.just(new WebFluxApiResponse(response.statusCode(), "Error response"));
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
