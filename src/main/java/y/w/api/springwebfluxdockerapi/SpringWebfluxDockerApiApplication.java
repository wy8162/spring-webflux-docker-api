package y.w.api.springwebfluxdockerapi;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.GreetingService.ErrorMessage;
import y.w.api.springwebfluxdockerapi.GreetingService.GreetingRequest;
import y.w.api.springwebfluxdockerapi.GreetingService.GreetingResponse;

@RequiredArgsConstructor
@SpringBootApplication
public class SpringWebfluxDockerApiApplication {
    private final ServiceHandler serviceHandler;

    @Bean
    RouterFunction<ServerResponse> routes(GreetingService service) {
        return
            route(GET("/hello/{name}"),
                r -> {
                    Mono<GreetingResponse> gr = service.hello(new GreetingRequest(r.pathVariable("name")));
                    return ServerResponse.ok().body(BodyInserters.fromPublisher(gr, GreetingResponse.class));
                })
            .and(route(GET("/hello"), serviceHandler::helloHandler))
            .and(route(RequestPredicates.all(), r -> ServerResponse.status(HttpStatus.NOT_FOUND).body(BodyInserters.fromValue(new ErrorMessage("Not Found")))));
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxDockerApiApplication.class, args);
    }
}

@RequiredArgsConstructor
@Component
class ServiceHandler {
    private final GreetingService service;

    public Mono<ServerResponse> helloHandler(ServerRequest request) {
        Mono<GreetingResponse> gr = service.hello(new GreetingRequest("World"));

        return ServerResponse.ok()
            .body(
                BodyInserters.fromPublisher(gr, GreetingResponse.class)
            );
    }
}

@Service
class GreetingService {
    public Mono<GreetingResponse> hello(GreetingRequest request) {
        return Mono.just(new GreetingResponse(String.format("Hello, %s", (request.getName() == null ? "world" : request.getName()))));
    }

    @Data
    @AllArgsConstructor
    static class GreetingRequest {
        private final String name;
    }

    @Data
    @AllArgsConstructor
    static class GreetingResponse {
        private final String message;
    }

    @Data
    @AllArgsConstructor
    static class ErrorMessage {
        private final String message;
    }
}