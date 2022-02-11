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
