package y.w.api.springwebfluxdockerapi.fruit;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.fruit.Fruit;

@Component
public class FruitServiceHandler {
    public static String DEFAULT_FRUIT_ID = UUID.randomUUID().toString();

    public Mono<ServerResponse> getFruitById(ServerRequest request) {
        Fruit f = Optional.ofNullable(request.pathVariables().get("id"))
            .map(id -> fruits.getOrDefault(id, new Fruit(UUID.randomUUID().toString(), "Apple", 5)))
            .orElse(new Fruit(UUID.randomUUID().toString(), "Apple", 5));

        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(f), Fruit.class);
    }

    public Mono<ServerResponse> getAllFruit(ServerRequest request) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(Flux.fromIterable(fruits.values()), Fruit.class);
    }

    private final static Map<String, Fruit> fruits = new HashMap<>();

    static {
        fruits.put(DEFAULT_FRUIT_ID, new Fruit(DEFAULT_FRUIT_ID, "Pineapple", 3));

        for (int i = 0; i < 10; i++) {
            String key = UUID.randomUUID().toString();
            fruits.put(key, new Fruit(key, "Apple", 10));
            key = UUID.randomUUID().toString();
            fruits.put(key, new Fruit(key, "Pear", 1));
            key = UUID.randomUUID().toString();
            fruits.put(key, new Fruit(key, "Peach", 5));
        }
    }
}
