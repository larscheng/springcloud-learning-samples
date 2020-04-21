package com.larscheng.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudGatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayServerApplication.class, args);
    }


    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/hangzhou/**")
                        .filters(f -> f.filter(new MyGateWayFilter())
                                .addResponseHeader("X-Response-Default-Foo", "Default-Bar"))
                        .uri("lb://hellohangzhoudemo")
                        .order(0)
                        .id("hellohangzhoudemo")
                )
                .build();
    }

    @Bean
    public RequestUseTimeGatewayFilterFactory requestUseTimeGatewayFilterFactory() {
        return new RequestUseTimeGatewayFilterFactory();
    }
}
