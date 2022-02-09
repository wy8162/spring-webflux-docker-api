package y.w.api.springwebfluxdockerapi.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.pojo.ApiRequest;
import y.w.api.springwebfluxdockerapi.pojo.ApiResponse;

@Service
public class GreetingService {
    public Mono<ApiResponse> hello(ApiRequest request) {
        return Mono.just(new ApiResponse(String.format("Hello, %s", (request.getName() == null ? "world" : request.getName()))));
    }
}
