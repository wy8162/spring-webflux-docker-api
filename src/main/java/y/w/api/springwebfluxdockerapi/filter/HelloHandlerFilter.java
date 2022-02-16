package y.w.api.springwebfluxdockerapi.filter;


import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Impacts to specific endpoint
 */
public class HelloHandlerFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {
        if (request.pathVariables().getOrDefault("name", "").equals("wy"))
            return ServerResponse.status(HttpStatus.FORBIDDEN).build();

        return next.handle(request);
    }
}
