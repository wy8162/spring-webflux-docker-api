package y.w.api.springwebfluxdockerapi.route;

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
import y.w.api.springwebfluxdockerapi.handler.CustomerAccountHandler;
import y.w.api.springwebfluxdockerapi.handler.FibHandler;
import y.w.api.springwebfluxdockerapi.handler.MathHandler;
import y.w.api.springwebfluxdockerapi.handler.ServiceHandler;
import y.w.api.springwebfluxdockerapi.pojo.ApiRequest;
import y.w.api.springwebfluxdockerapi.pojo.ApiResponse;
import y.w.api.springwebfluxdockerapi.pojo.CustomException;
import y.w.api.springwebfluxdockerapi.pojo.ErrorMessage;
import y.w.api.springwebfluxdockerapi.service.GreetingService;
import y.w.api.springwebfluxdockerapi.service.MathService;

@RequiredArgsConstructor
@Configuration
public class RouteConfiguration {

    private final GreetingService service;
    private final ServiceHandler serviceHandler;
    private final CustomerAccountHandler customerAccountHandler;
    private final FibHandler fibHandler;
    private final MathHandler mathHandler;

    @Bean
    RouterFunction<ServerResponse> apiRoutes() {
        return route()
            .GET("/hello/{name}",
                accept(APPLICATION_JSON),
                r -> {
                    Mono<ApiResponse> gr = service.hello(new ApiRequest(r.pathVariable("name")));
                    return ServerResponse.ok()
                        .body(BodyInserters.fromPublisher(gr, ApiResponse.class));
                })
            .GET("/hello",
                accept(APPLICATION_JSON),
                serviceHandler::helloHandler)
            .GET("/books/init",
                accept(APPLICATION_JSON),
                serviceHandler::initBooks)
            .GET("/books",
                accept(APPLICATION_JSON),
                serviceHandler::getAllBooks)
            // Path pattern: /some/someValue/others?id=someId
            .GET("/some/{pathVariable}/others",
                accept(APPLICATION_JSON),
                serviceHandler::testPathVaribles)
            .GET("api/relationship",
                accept(APPLICATION_JSON),
                customerAccountHandler::getAllCustomerRelationships
            )
            .GET("api/permission",
                accept(APPLICATION_JSON),
                customerAccountHandler::getAllPermissions
            )
            .GET("api/account",
                accept(APPLICATION_JSON),
                customerAccountHandler::getAllAccounts
            )
            .GET("api/ca/{profileId}",
                accept(APPLICATION_JSON),
                customerAccountHandler::retrieveAccountsByProfileId
            )
            .GET("/api/fib",
                accept(MediaType.TEXT_EVENT_STREAM),
                fibHandler::getFib
            )
            .GET("/api/math",
                accept(APPLICATION_JSON),
                mathHandler::compute
            )
            .GET("/api/exceptionHandling",
                accept(APPLICATION_JSON),
                r -> Mono.error(new CustomException("Test Exception handling"))
            )
            // This is to capture the Mono.error above.
            .onError(CustomException.class, errHandler())
            // The following is supposed to be handled by GlobalErrorWebExceptionHandler
            .GET("/api/globalExceptionHandling",
                accept(APPLICATION_JSON),
                r -> {
                    throw new CustomException("Test Exception handling");
                }
            )
            .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> errHandler() {
        return (ex, request) -> {
            return ServerResponse.badRequest().bodyValue(new ErrorMessage(ex.getMessage()));
        };
    }
}
