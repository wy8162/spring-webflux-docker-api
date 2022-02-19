package y.w.api.springwebfluxdockerapi;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class HealthChecker {

    public static boolean healthCheck() {
        try {
            URL url = new URL("http://localhost:8080/api/health");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            return conn.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            System.out.println("Skip test because the application is not running.");
            return false;
        }
    }

    public static boolean healthCheck2(WebClient webClient) {
        Mono<String> status = webClient
            .get()
            .uri("/api/health")
            .accept(MediaType.APPLICATION_JSON)
            .exchangeToMono(r -> {
                if (r.statusCode().equals(HttpStatus.OK))
                    return Mono.just("HEALTHY");
                else
                    return Mono.empty();
            });

        Optional<String> r = Optional.empty();
        try {
            r = status.blockOptional();
        } catch (Exception e) {
            System.out.println("Skip test because the application is not running.");
        }

        return r.isPresent();
    }
}
