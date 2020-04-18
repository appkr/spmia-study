package dev.appkr.apigateway;

import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootApplication
public class ApigatewayApplication {

    public static final String TRACE_ID   = "x-b3-traceid";
    public static final String AUTH_TOKEN = "tmx-auth-token";
    public static final String USER_ID    = "tmx-user-id";
    public static final String ORG_ID     = "tmx-org-id";

    /**
     * Referenced from @see https://cloud.spring.io/spring-cloud-gateway/2.1.x/single/spring-cloud-gateway.html#_fluent_java_routes_api
     */
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

    /**
     * Referenced from @see https://cloud.spring.io/spring-cloud-gateway/2.1.x/single/spring-cloud-gateway.html#_writing_custom_global_filters
     */
    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> chain.filter(exchange)
            .then(Mono.just(exchange))
            .map(serverWebExchange -> {
                List<String> concern = serverWebExchange.getRequest().getHeaders().get(TRACE_ID);
                String traceId = (concern != null && concern.size() > 0) ? concern.get(0) : TraceId.create();

                // Keep traceId in MDC
                MDC.put(TRACE_ID, traceId);

                // assign a x-b3-traceid request header on the downstream request
                serverWebExchange.getRequest().mutate()
                    .header(TRACE_ID, traceId)
                    .build();

                // set the x-b3-traceid response header
                serverWebExchange.getResponse().getHeaders()
                    .set(TRACE_ID, traceId);

                return serverWebExchange;
            })
            .then();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApigatewayApplication.class, args);
    }

}
