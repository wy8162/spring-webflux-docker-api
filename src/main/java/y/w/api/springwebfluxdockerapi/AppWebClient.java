package y.w.api.springwebfluxdockerapi;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.pojo.ApiResponse;

@Component
public class AppWebClient {
    private final WebClient webClient;

    public AppWebClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8080").build();
    }

    public Mono<String> getMessage() {
        return this.webClient
            .get()
            .uri("/hello")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ApiResponse.class)
            .map(ApiResponse::getMessage);
    }
}
