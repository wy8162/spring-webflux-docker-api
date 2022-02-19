package y.w.api.springwebfluxdockerapi.general;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.general.MathDTO;
import y.w.api.springwebfluxdockerapi.general.MathService;

@Component
@RequiredArgsConstructor
public class MathHandler {
    private final MathService service;

    public Mono<ServerResponse> compute(ServerRequest request) {
        Mono<MathDTO> mathDTOMono = service.compute(request, request.bodyToMono(MathDTO.class));

        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(mathDTOMono, MathDTO.class);
    }
}
