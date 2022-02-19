package y.w.api.springwebfluxdockerapi.ca;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.util.function.BiFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.pojo.ErrorMessage;

@RequiredArgsConstructor
@Configuration
public class CustomerAccountRoutes {

    private final CustomerAccountHandler customerAccountHandler;

    // This defines the root context
    @Bean
    RouterFunction<ServerResponse> apiRootCa() {
        return route()
            .path("/ca", this::apiRoutes).build();
    }

    /**
     * The following will be based on /api
     * @return
     */
    private RouterFunction<ServerResponse> apiRoutes() {
        return route()
            .GET("relationship",
                accept(APPLICATION_JSON),
                customerAccountHandler::getAllCustomerRelationships
            )
            .GET("permission",
                accept(APPLICATION_JSON),
                customerAccountHandler::getAllPermissions
            )
            .GET("account",
                accept(APPLICATION_JSON),
                customerAccountHandler::getAllAccounts
            )
            .GET("ca/{profileId}",
                accept(APPLICATION_JSON),
                customerAccountHandler::retrieveAccountsByProfileId
            )
            .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> errHandler() {
        return (ex, request) -> ServerResponse.badRequest().bodyValue(new ErrorMessage(ex.getMessage()));
    }
}
