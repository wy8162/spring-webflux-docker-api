package y.w.api.springwebfluxdockerapi.httpclient;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@RequiredArgsConstructor
@Configuration
public class WebClientConfiguration {
    @Bean
    WebClient webClient() {
        HttpClient httpClient = HttpClient.create();
        return WebClient
            .builder()
            .baseUrl("http://localhost:8080")
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

}
