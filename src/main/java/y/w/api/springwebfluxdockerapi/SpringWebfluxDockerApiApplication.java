package y.w.api.springwebfluxdockerapi;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.pojo.ErrorMessage;
import y.w.api.springwebfluxdockerapi.pojo.GreetingRequest;
import y.w.api.springwebfluxdockerapi.pojo.GreetingResponse;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class SpringWebfluxDockerApiApplication {
    private final ServiceHandler serviceHandler;

    @Bean
    RouterFunction<ServerResponse> routes(GreetingService service) {
        return
            route(GET("/hello/{name}").and(accept(MediaType.APPLICATION_JSON)),
                r -> {
                    Mono<GreetingResponse> gr = service.hello(new GreetingRequest(r.pathVariable("name")));
                    return ServerResponse.ok().body(BodyInserters.fromPublisher(gr, GreetingResponse.class));
                })
                .and(route(GET("/hello").and(accept(MediaType.APPLICATION_JSON)), serviceHandler::helloHandler))
                .and(route(GET("/books").and(accept(MediaType.APPLICATION_JSON)), serviceHandler::getAllBooks))
                .and(route(RequestPredicates.all(), r -> ServerResponse.status(HttpStatus.NOT_FOUND).body(BodyInserters.fromValue(new ErrorMessage("Not Found")))));
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringWebfluxDockerApiApplication.class, args);

        AppWebClient client = ctx.getBean(AppWebClient.class);

        log.info("Hello message: {}", client.getMessage().block());
    }
}
