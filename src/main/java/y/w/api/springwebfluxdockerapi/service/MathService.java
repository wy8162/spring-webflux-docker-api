package y.w.api.springwebfluxdockerapi.service;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.model.MathDTO;

@Slf4j
@Service
public class MathService {
    public Mono<MathDTO> compute(ServerRequest request, Mono<MathDTO> mathDTO) {
        var hv = request.headers().header("error")
            .stream()
            .filter(v -> v.equals("ignore"))
            .findAny();

        return mathDTO
            .flatMap(mathDTO1 -> Mono.just(computeResult(mathDTO1, hv)))
            .doOnError(e -> {
                log.error("Exception: {}", e.getMessage());
            })
            .onErrorReturn(MathDTO.builder().answer("BAD QUESTION").build());
    }

    private MathDTO computeResult(MathDTO mathDTO, Optional<String> headerValue) {
        switch (mathDTO.getOperator()) {
            case "+":
                mathDTO.setAnswer(String.format("%d", mathDTO.getFirstNumber() + mathDTO.getSecondNumber()));
                break;
            case "-":
                mathDTO.setAnswer(String.format("%d", mathDTO.getFirstNumber() - mathDTO.getSecondNumber()));
                break;
            case "/":
                if (headerValue.isPresent() && mathDTO.getSecondNumber() == 0)
                    mathDTO.setAnswer("IGNORED - DIVIDED by ZERO");
                else
                    mathDTO.setAnswer(String.format("%d", mathDTO.getFirstNumber() / mathDTO.getSecondNumber()));
                break;
            case "*":
                mathDTO.setAnswer(String.format("%d", mathDTO.getFirstNumber() * mathDTO.getSecondNumber()));
                break;
            default:
                mathDTO.setAnswer("BAD QUESTION");
        }
        return mathDTO;
    }
}
