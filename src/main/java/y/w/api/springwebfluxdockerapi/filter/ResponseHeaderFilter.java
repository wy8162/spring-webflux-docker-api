package y.w.api.springwebfluxdockerapi.filter;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


/**
 * WebFilter impacts all endpoint
 */
@Component
public class ResponseHeaderFilter implements WebFilter {

    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        exchange
            .getResponse()
            .getHeaders().add("testResponseHeader", "testValue"); // Add header to respponse

        return chain.filter(exchange);
    }
}
