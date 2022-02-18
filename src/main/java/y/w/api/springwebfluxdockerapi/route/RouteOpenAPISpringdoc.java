package y.w.api.springwebfluxdockerapi.route;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static y.w.api.springwebfluxdockerapi.handler.FruitServiceHandler.DEFAULT_FRUIT_ID;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import y.w.api.springwebfluxdockerapi.handler.FruitServiceHandler;
import y.w.api.springwebfluxdockerapi.model.Fruit;

@RequiredArgsConstructor
@Configuration
public class RouteOpenAPISpringdoc {
    private final FruitServiceHandler fruitServiceHandler;

    @Bean
    RouterFunction<ServerResponse> openApiRoutes() {
        return route()
            .GET("/fruit",
                accept(MediaType.APPLICATION_JSON),
                fruitServiceHandler::getAllFruit,
                ops -> ops.tag("fruit")
                    .beanClass(FruitServiceHandler.class).beanMethod("getAllFruit")
                    .operationId("getAllFruit").summary("Find all fruits").tags(new String[] { "Fruit Service" })
                    .response(responseBuilder().responseCode("200").description("successful operation").implementation(Fruit.class))
                    .response(responseBuilder().responseCode("400").description("Invalid Fruit ID supplied"))
                    .response(responseBuilder().responseCode("404").description("Fruit not found"))
            )
            .GET("/fruit/{id}",
                accept(MediaType.APPLICATION_JSON),
                fruitServiceHandler::getFruitById,
                ops -> ops.tag("fruit")
                    .beanClass(FruitServiceHandler.class).beanMethod("getFruitById")
                    .operationId("getFruitById").summary("Find fruit by ID").tags(new String[] { "Fruit Service" })
                    .parameter(parameterBuilder().in(ParameterIn.PATH).name("id").description("Fruit Id").example(DEFAULT_FRUIT_ID))
                    .response(responseBuilder().responseCode("200").description("successful operation").implementation(Fruit.class))
                    .response(responseBuilder().responseCode("400").description("Invalid Fruit ID supplied"))
                    .response(responseBuilder().responseCode("404").description("Fruit not found"))
            )
            .build();
    }
}
