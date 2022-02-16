package y.w.api.springwebfluxdockerapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * There are a few ways to set authencation credentials.
 *
 * 1. Add header to requests
 * 2. Add default header.
 * 3. Use filter like filterToAddToken
 * 4. Use filter like RequestHeaderFilter, etc.
 *
 */
@Slf4j
@ExtendWith(SpringExtension.class)
public class WebClientAuthAttributesTest {
    private WebClient webClient;

    @BeforeEach
    public void setUp() {
        webClient = WebClient
            .builder()
            .baseUrl("http://localhost:8080")
            // Set a default header for all requests.
            //.defaultHeaders(h -> h.setBearerAuth("toke...."))
            .filter(this::filterToAddToken)
            .build();
    }


    // Version 1
    private Mono<ClientResponse> filterToAddToken(ClientRequest request, ExchangeFunction exchange) {
        // ClientRequest is immutable. So let's create a new one.
        ClientRequest clientRequest = ClientRequest.from(request)
            .headers(h -> h.setBearerAuth("sometokensfsdfafafasgassasasadfsdfs")).build();

        return exchange.exchange(clientRequest);
    }


    // Version 2
    private Mono<ClientResponse> filterToAddTokenWithAttributes(ClientRequest request, ExchangeFunction exchange) {
        // ClientRequest is immutable. So let's create a new one.
        ClientRequest clientRequest = request
            .attribute("auth")
            .map(a -> a.equals("basic") ? withBasicAuth(request) : withOAuth(request))
            .orElse(request);

        return exchange.exchange(clientRequest);
    }

    private ClientRequest withBasicAuth(ClientRequest request) {
        return ClientRequest.from(request)
            .headers(h -> h.setBearerAuth("sometokensfsdfafafasgassasasadfsdfs")).build();
    }

    private ClientRequest withOAuth(ClientRequest request) {
        return ClientRequest.from(request)
            .headers(h -> h.setBearerAuth("sometokensfsdfafafasgassasasadfsdfs")).build();
    }


    @Test
    void testHelloCheckingStatus() {
// Method 1;
//        URI uri = UriComponentsBuilder
//            .fromUriString("http://localhost:8080/some/{pv}/others?id={qv}")
//            .build("pathVariable", "queryVar");

/// Method 2
//        URI uri = UriComponentsBuilder
//            .fromUriString("http://localhost:8080/some/{pv}/others")
//            .query("id={qv}")
//            .build("pathVariable", "queryVar");

        URI uri = UriComponentsBuilder
            .newInstance()
            .scheme("http")
            .host("localhost")
            .port(8080)
            .path("/some/{pv}/others")
            .query("id={qv}")
            .build("pathVariable", "queryVar");

        Mono<String> responseMono = webClient
            .get()
            .uri(uri)
            // Auth per request.
            // Or set defaultHeaders in the WebClient globally. See above.
            //.headers(h -> h.setBearerAuth("sometokenaaoanfomdsfjsofsdFhofjOFDpFjpfjdpfjdpfjsdfjij"))

            // Set attribute to control what authentication is used.
            .attribute("auth", "basic")
            .accept(MediaType.APPLICATION_JSON)
            .exchangeToMono(response -> {
                if (response.statusCode().equals(HttpStatus.OK))
                    return response.bodyToMono(String.class);
                else if (response.statusCode().is4xxClientError()){
                    return Mono.just("Error response");
                } else {
                    return response.createException()
                        .flatMap(Mono::error);
                }
            });

        responseMono.as(StepVerifier::create)
            .assertNext(actual -> {
                assertThat(actual).contains("pathVariable");
                assertThat(actual).contains("queryVar");
                log.info(actual);
            })
            .verifyComplete();
    }
}
