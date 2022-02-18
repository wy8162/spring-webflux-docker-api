package y.w.api.springwebfluxdockerapi.route;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.filter.HelloHandlerFilter;
import y.w.api.springwebfluxdockerapi.handler.CustomerAccountHandler;
import y.w.api.springwebfluxdockerapi.handler.FibHandler;
import y.w.api.springwebfluxdockerapi.handler.MathHandler;
import y.w.api.springwebfluxdockerapi.handler.ServiceHandler;
import y.w.api.springwebfluxdockerapi.pojo.WebFluxApiRequest;
import y.w.api.springwebfluxdockerapi.pojo.WebFluxApiResponse;
import y.w.api.springwebfluxdockerapi.service.GreetingService;
import y.w.api.springwebfluxdockerapi.service.stockquote.QuoteHandler;

@RequiredArgsConstructor
@Configuration
public class RouteConfiguration {

    private final GreetingService service;
    private final ServiceHandler serviceHandler;
    private final CustomerAccountHandler customerAccountHandler;
    private final FibHandler fibHandler;
    private final MathHandler mathHandler;

    @RouterOperations({
        @RouterOperation(
            path = "/hello/{name}",
            method = RequestMethod.GET,
            beanClass = QuoteHandler.class,
            beanMethod = "hello",
            operation = @Operation(
                operationId = "hello",
                description = "Hello World",
                requestBody = @RequestBody(description = "Say hello to user", content = { @Content( mediaType = "application/json")}),
                parameters = { @Parameter(in = ParameterIn.PATH, name = "name", description = "Name of the user", example = "Jack")},
                responses = { @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content( mediaType = "application/json",schema = @Schema(implementation = WebFluxApiResponse.class)))}
            ))
    })
    @Bean
    RouterFunction<ServerResponse> apiRoutes() {
        return route()
            .GET("/hello/{name}",
                accept(APPLICATION_JSON),
                r -> {
                    Mono<WebFluxApiResponse> gr = service.hello(new WebFluxApiRequest(r.pathVariable("name")));
                    return ServerResponse.ok()
                        .body(BodyInserters.fromPublisher(gr, WebFluxApiResponse.class));
                })
            .filter(new HelloHandlerFilter())
            .GET("/hello",
                accept(APPLICATION_JSON),
                serviceHandler::helloHandler)
            // Path pattern: /some/someValue/others?id=someId
            .GET("/some/{pathVariable}/others",
                accept(APPLICATION_JSON),
                serviceHandler::testPathVaribles)
            .build();
    }
}
