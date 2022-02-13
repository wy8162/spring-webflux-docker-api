package y.w.api.springwebfluxdockerapi.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

/**
 * https://www.baeldung.com/flux-sequences-reactor
 *
 * NOTE: this is just a demo for how to use programatic creation of flux. The
 * Integer Fib can quickly get overflown.
 */
@Service
public class FibonacciService {

    public Flux<Integer> getFibonacci() {
        return Flux.generate(
            () -> Tuples.of(0, 1),
            (state, sink) -> {
                sink.next(state.getT1());
                return Tuples.of(state.getT2(), state.getT1() + state.getT2());
            }
        );
    }

}
