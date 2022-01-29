package y.w.api.springwebfluxdockerapi;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.pojo.GreetingRequest;
import y.w.api.springwebfluxdockerapi.pojo.GreetingResponse;

@Service
class GreetingService {
    public Mono<GreetingResponse> hello(GreetingRequest request) {
        return Mono.just(new GreetingResponse(String.format("Hello, %s", (request.getName() == null ? "world" : request.getName()))));
    }
}
