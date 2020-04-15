package dev.appkr.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApigatewayApplication {

    @Bean
    RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(r -> r.path("/org/**")
                .filters(f -> f.rewritePath("/org/(?<remaining>.*)", "/${remaining}"))
                .uri("lb://ORGANIZATION-SERVICE:8090"))
            .route(r -> r.path("/lic/**")
                .filters(f -> f.rewritePath("/lic/(?<remaining>.*)", "/${remaining}"))
                .uri("lb://LICENSE-SERVICE:8080"))
            .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApigatewayApplication.class, args);
    }

}
