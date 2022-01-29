package y.w.api.springwebfluxdockerapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.pojo.BookResponse;
import y.w.api.springwebfluxdockerapi.pojo.GreetingRequest;
import y.w.api.springwebfluxdockerapi.pojo.GreetingResponse;
import y.w.api.springwebfluxdockerapi.repository.BookRepository;

@RequiredArgsConstructor
@Component
class ServiceHandler {
    private final GreetingService service;
    private final BookRepository bookRepository;

    public Mono<ServerResponse> helloHandler(ServerRequest request) {
        Mono<GreetingResponse> gr = service.hello(new GreetingRequest("World"));

        return ServerResponse.ok()
            .body(
                BodyInserters.fromPublisher(gr, GreetingResponse.class)
            );
    }

    public Mono<ServerResponse> getAllBooks(ServerRequest request) {
        Mono<BookResponse> responseMono = bookRepository
            .findAll()
            .collectList()
            .map(list -> new BookResponse(list));

        return ServerResponse.ok().body(BodyInserters.fromPublisher(responseMono, BookResponse.class));
    }
}
