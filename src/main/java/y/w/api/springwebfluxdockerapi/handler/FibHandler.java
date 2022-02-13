package y.w.api.springwebfluxdockerapi.handler;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.service.FibonacciService;

@RequiredArgsConstructor
@Component
public class FibHandler {
    private final FibonacciService fibonacciService;

    public Mono<ServerResponse> getFib(ServerRequest request) {
        return ServerResponse
            .ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(fibonacciService.getFibonacci().delaySequence(Duration.ofSeconds(2)), Integer.class);
    }
}
