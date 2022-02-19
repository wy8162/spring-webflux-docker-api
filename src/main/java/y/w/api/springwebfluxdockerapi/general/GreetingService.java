package y.w.api.springwebfluxdockerapi.general;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.pojo.WebFluxApiRequest;
import y.w.api.springwebfluxdockerapi.pojo.WebFluxApiResponse;

@Service
public class GreetingService {
    public Mono<WebFluxApiResponse> hello(WebFluxApiRequest request) {
        return Mono.just(new WebFluxApiResponse(String.format("Hello, %s", (request.getName() == null ? "world" : request.getName()))));
    }
}
