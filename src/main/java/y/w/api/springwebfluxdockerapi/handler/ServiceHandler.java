package y.w.api.springwebfluxdockerapi.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.service.GreetingService;
import y.w.api.springwebfluxdockerapi.SampleDataInitializer;
import y.w.api.springwebfluxdockerapi.pojo.ApiRequest;
import y.w.api.springwebfluxdockerapi.pojo.ApiResponse;
import y.w.api.springwebfluxdockerapi.pojo.BookResponse;
import y.w.api.springwebfluxdockerapi.repository.BookRepository;

@RequiredArgsConstructor
@Component
public class ServiceHandler {
    private final GreetingService service;
    private final BookRepository bookRepository;
    private final SampleDataInitializer sampleDataInitializer;

    public Mono<ServerResponse> helloHandler(ServerRequest request) {
        Mono<ApiResponse> gr = service.hello(new ApiRequest("World"));

        return ServerResponse.ok()
            .body(
                BodyInserters.fromPublisher(gr, ApiResponse.class)
            );
    }

    public Mono<ServerResponse> getAllBooks(ServerRequest request) {
        Mono<BookResponse> responseMono = bookRepository
            .findAll()
            .collectList()
            .map(list -> new BookResponse(list));

        return ServerResponse.ok().body(BodyInserters.fromPublisher(responseMono, BookResponse.class));
    }

    public Mono<ServerResponse> initBooks(ServerRequest request) {
        sampleDataInitializer.initializeSampleData((int)(Math.random() * 100));

        return ServerResponse.ok().body(BodyInserters.fromValue(new ApiResponse("OK - Initialized")));
    }

    public Mono<ServerResponse> testPathVaribles(ServerRequest request) {
        Mono<String> stringMono = Mono.just(String.format("PathVariable= %s, Query of id=%s",
            request.pathVariable("pathVariable"),
            request.queryParam("id").orElse("NONE")));

        return ServerResponse
            .ok()
            .body(BodyInserters.fromPublisher(stringMono, String.class));
    }
}
