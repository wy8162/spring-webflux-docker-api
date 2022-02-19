package y.w.api.springwebfluxdockerapi.quote;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_NDJSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@RequiredArgsConstructor
@Configuration
public class QuoteRouteConfiguration {
    public static final String QUOTES_PATH = "/quotes";

    private final QuoteHandler quoteHandler;

    @Bean
    RouterFunction<ServerResponse> quoteRoutes() {
        return route()
            .GET(QUOTES_PATH, accept(APPLICATION_JSON), quoteHandler::fetchQuotes)
            .GET(QUOTES_PATH, accept(APPLICATION_NDJSON), quoteHandler::streamQuotes)
            .build();
    }
}
