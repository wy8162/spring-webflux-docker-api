package y.w.api.springwebfluxdockerapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI demoOpenApiDocumentations(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
            .components(new Components())
            .info(new Info()
                .title("Webflux Practice API")
                .description("Spring Spring-webflux API sample application")
                .version(appVersion)
                .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                .contact(new Contact()
                    .name("WYANG")
                    .url("https://github.com/wy8162")
                    .email("wy8162@gmail.com")));
    }
}
