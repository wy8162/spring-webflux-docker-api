package y.w.api.springwebfluxdockerapi.route;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.handler.CustomerAccountHandler;
import y.w.api.springwebfluxdockerapi.handler.ServiceHandler;
import y.w.api.springwebfluxdockerapi.pojo.ApiRequest;
import y.w.api.springwebfluxdockerapi.pojo.ApiResponse;
import y.w.api.springwebfluxdockerapi.service.GreetingService;

@RequiredArgsConstructor
@Configuration
public class RouteConfiguration {

    private final GreetingService service;
    private final ServiceHandler serviceHandler;
    private final CustomerAccountHandler customerAccountHandler;

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
//            .route(RequestPredicates.all(),
//                r -> ServerResponse.status(HttpStatus.NOT_FOUND)
//                    .body(BodyInserters.fromValue(new ErrorMessage("Not Found"))))
            .build();
    }
}
