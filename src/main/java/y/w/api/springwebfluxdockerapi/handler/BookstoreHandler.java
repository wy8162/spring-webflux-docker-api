package y.w.api.springwebfluxdockerapi.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class BookstoreHandler {
    public Mono<ServerResponse> getAllBooks(ServerRequest request) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just("TODO"), String.class);

    }


    public Mono<ServerResponse> getBookById(ServerRequest request) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just("TODO"), String.class);
    }
}
