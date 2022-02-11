package y.w.api.springwebfluxdockerapi.route;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_NDJSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.handler.CustomerAccountHandler;
import y.w.api.springwebfluxdockerapi.handler.ServiceHandler;
import y.w.api.springwebfluxdockerapi.pojo.ApiRequest;
import y.w.api.springwebfluxdockerapi.pojo.ApiResponse;
import y.w.api.springwebfluxdockerapi.pojo.ErrorMessage;
import y.w.api.springwebfluxdockerapi.service.GreetingService;
import y.w.api.springwebfluxdockerapi.service.stockquote.QuoteHandler;

@RequiredArgsConstructor
@Configuration
public class RouteConfiguration {
    public static final String QUOTES_PATH = "/quotes";

    private final ServiceHandler serviceHandler;
    private final CustomerAccountHandler customerAccountHandler;
    private final QuoteHandler quoteHandler;

    @Bean
    RouterFunction<ServerResponse> routes(GreetingService service) {
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
            .GET(QUOTES_PATH, accept(APPLICATION_JSON), quoteHandler::fetchQuotes)
            .GET(QUOTES_PATH, accept(APPLICATION_NDJSON), quoteHandler::streamQuotes)
            .route(RequestPredicates.all(),
                r -> ServerResponse.status(HttpStatus.NOT_FOUND)
                    .body(BodyInserters.fromValue(new ErrorMessage("Not Found"))))
            .build();
    }
}
