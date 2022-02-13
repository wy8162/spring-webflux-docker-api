package y.w.api.springwebfluxdockerapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.model.MathDTO;

@Slf4j
@Service
public class MathService {
    public Mono<MathDTO> compute(Mono<MathDTO> mathDTO) {
        return mathDTO
            .flatMap(mathDTO1 -> Mono.just(computeResult(mathDTO1)))
            .doOnError(e -> {
                log.error("Exception: {}", e.getMessage());
            })
            .onErrorReturn(MathDTO.builder().answer("BAD QUESTION").build());
    }

    private MathDTO computeResult(MathDTO mathDTO) {
        switch (mathDTO.getOperator()) {
            case "+":
                mathDTO.setAnswer(String.format("%d", mathDTO.getFirstNumber() + mathDTO.getSecondNumber()));
                break;
            case "-":
                mathDTO.setAnswer(String.format("%d", mathDTO.getFirstNumber() - mathDTO.getSecondNumber()));
                break;
            case "/":
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
