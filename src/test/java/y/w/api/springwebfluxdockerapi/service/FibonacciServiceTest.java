package y.w.api.springwebfluxdockerapi.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import y.w.api.springwebfluxdockerapi.general.FibonacciService;

class FibonacciServiceTest {

    @Test
    void getFibonacci() {
        FibonacciService fibonacciService = new FibonacciService();

        Flux<Integer> fibFlux = fibonacciService.getFibonacci().take(5);

        StepVerifier.create(fibFlux)
            .expectNext(0, 1, 1, 2, 3)
            .expectComplete()
            .verify();

        fibonacciService.getFibonacci().take(50)
            .log()
            .subscribe();
    }
}