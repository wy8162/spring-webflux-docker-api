package y.w.api.springwebfluxdockerapi.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.SampleDataInitializer;
import y.w.api.springwebfluxdockerapi.pojo.ApiRequest;
import y.w.api.springwebfluxdockerapi.pojo.ApiResponse;
import y.w.api.springwebfluxdockerapi.repository.BookMongoRepository;
import y.w.api.springwebfluxdockerapi.service.GreetingService;

@Slf4j
@RequiredArgsConstructor
@Component
public class ServiceHandler {
    private final GreetingService service;
    private final BookMongoRepository bookRepository;
    private final SampleDataInitializer sampleDataInitializer;

    public Mono<ServerResponse> helloHandler(ServerRequest request) {
        Mono<ApiResponse> gr = service.hello(new ApiRequest("World"));

        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromPublisher(gr, ApiResponse.class)
            );
    }

    public Mono<ServerResponse> testPathVaribles(ServerRequest request) {
        Mono<String> stringMono = Mono.just(String.format("PathVariable= %s, Query of id=%s",
            request.pathVariable("pathVariable"),
            request.queryParam("id").orElse("NONE")));

        log.info("Header: {}", request.headers());

        return ServerResponse
            .ok()
            .contentType(MediaType.TEXT_PLAIN)
            .body(BodyInserters.fromPublisher(stringMono, String.class));
    }
}
