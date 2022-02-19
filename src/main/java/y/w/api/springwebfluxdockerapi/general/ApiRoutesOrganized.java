package y.w.api.springwebfluxdockerapi.general;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.util.function.BiFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.ca.CustomerAccountHandler;
import y.w.api.springwebfluxdockerapi.pojo.CustomException;
import y.w.api.springwebfluxdockerapi.pojo.ErrorMessage;
import y.w.api.springwebfluxdockerapi.pojo.WebFluxApiRequest;
import y.w.api.springwebfluxdockerapi.pojo.WebFluxApiResponse;

@RequiredArgsConstructor
@Configuration
public class ApiRoutesOrganized {

    private final GreetingService service;
    private final ServiceHandler serviceHandler;
    private final CustomerAccountHandler customerAccountHandler;
    private final FibHandler fibHandler;
    private final MathHandler mathHandler;

    // This defines the root context
    @Bean
    RouterFunction<ServerResponse> apiRootGeneral() {
        return route()
            .path("/api", this::apiRoutes).build();
    }

    /**
     * The following will be based on /api
     * @return
     */
    private RouterFunction<ServerResponse> apiRoutes() {
        return route()
            .GET("/health", r -> ServerResponse.ok().bodyValue("OK"))
            .GET("/fib",
                accept(MediaType.TEXT_EVENT_STREAM),
                fibHandler::getFib
            )
            .GET("/math",
                accept(APPLICATION_JSON),
                mathHandler::compute
            )
            .POST("/math",
                accept(APPLICATION_JSON),
                mathHandler::compute
            )
            .GET("/exceptionHandling",
                accept(APPLICATION_JSON),
                r -> Mono.error(new CustomException("Test Exception handling"))
            )
            // This is to capture the Mono.error above.
            .onError(CustomException.class, errHandler())
            // The following is supposed to be handled by GlobalErrorWebExceptionHandler
            .GET("/globalExceptionHandling",
                accept(APPLICATION_JSON),
                r -> {
                    throw new CustomException("Test Exception handling");
                }
            )
            .GET("/hello/{name}",
                accept(APPLICATION_JSON),
                r -> {
                    Mono<WebFluxApiResponse> gr = service.hello(new WebFluxApiRequest(r.pathVariable("name")));
                    return ServerResponse.ok()
                        .body(BodyInserters.fromPublisher(gr, WebFluxApiResponse.class));
                })
            //.filter(new HelloHandlerFilter())
            .GET("/hello",
                accept(APPLICATION_JSON),
                serviceHandler::helloHandler)
            // Path pattern: /some/someValue/others?id=someId
            .GET("/some/{pathVariable}/others",
                accept(APPLICATION_JSON),
                serviceHandler::testPathVaribles)
            .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> errHandler() {
        return (ex, request) -> ServerResponse.badRequest().bodyValue(new ErrorMessage(ex.getMessage()));
    }
}
