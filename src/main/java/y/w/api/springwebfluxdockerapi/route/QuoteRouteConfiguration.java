package y.w.api.springwebfluxdockerapi.route;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_NDJSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import y.w.api.springwebfluxdockerapi.service.stockquote.Quote;
import y.w.api.springwebfluxdockerapi.service.stockquote.QuoteHandler;

@RequiredArgsConstructor
@Configuration
public class QuoteRouteConfiguration {
    public static final String QUOTES_PATH = "/quotes";

    private final QuoteHandler quoteHandler;

    @RouterOperations({
        @RouterOperation(
            path = "/quotes",
            method = RequestMethod.GET,
            beanClass = QuoteHandler.class,
            beanMethod = "fetchQuotes",
            operation = @Operation(
                operationId = "fetchQuotes",
                description = "Get stock quotes",
                requestBody = @RequestBody(description = "Get quotes or streaming of quotes.", content = { @Content( mediaType = "application/json"), @Content( mediaType = "application/x-ndjson") }),
                parameters = {},
                responses = { @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content( mediaType = "application/json",schema = @Schema(implementation = Quote.class)))}
            ))
    })
    @Bean
    RouterFunction<ServerResponse> quoteRoutes() {
        return route()
            .GET(QUOTES_PATH, accept(APPLICATION_JSON), quoteHandler::fetchQuotes)
            .GET(QUOTES_PATH, accept(APPLICATION_NDJSON), quoteHandler::streamQuotes)
            .build();
    }
}
