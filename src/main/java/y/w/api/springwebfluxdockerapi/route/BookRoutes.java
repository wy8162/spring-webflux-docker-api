package y.w.api.springwebfluxdockerapi.route;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import y.w.api.springwebfluxdockerapi.handler.BookstoreHandler;
import y.w.api.springwebfluxdockerapi.model.bookstore.Book;

@RequiredArgsConstructor
@Configuration
public class BookRoutes {
    private final BookstoreHandler bookstoreHandler;

    @RouterOperations({
        @RouterOperation(
            path = "/books",
            method = RequestMethod.GET,
            beanClass = BookstoreHandler.class,
            beanMethod = "getAllBooks",
            operation = @Operation(
                operationId = "books",
                description = "Get all the books",
                parameters = {},
                responses = { @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Book.class)))}
            ))
    })
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
