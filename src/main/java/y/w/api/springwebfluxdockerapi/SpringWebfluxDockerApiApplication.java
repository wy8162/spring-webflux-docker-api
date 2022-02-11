package y.w.api.springwebfluxdockerapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class SpringWebfluxDockerApiApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringWebfluxDockerApiApplication.class, args);

        AppWebClient client = ctx.getBean(AppWebClient.class);

        log.info("Hello message: {}", client.getMessage().block());
    }
}
