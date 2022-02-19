package y.w.api.springwebfluxdockerapi.book;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@RequiredArgsConstructor
@Configuration
public class BookRoutes {
    private final BookstoreHandler bookstoreHandler;

    @Bean
    RouterFunction<ServerResponse> bookApiRoot() {
        return route()
            .path("/book", this::bookApiRoutes).build();
    }

    private RouterFunction<ServerResponse> bookApiRoutes() {
        return route()
            .GET("", accept(MediaType.APPLICATION_JSON), bookstoreHandler::getAllBooks)
            .build();
    }
}
