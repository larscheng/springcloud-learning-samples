package com.larscheng.www;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author: larscheng
 * @date: 2020/4/21 下午7:14
 * @description:
 *
 * 仿写 StripPrefixGatewayFilterFactory  自定义过滤器工厂
 * 在yml配置后，只对yml中的配置的路由进行过滤
 *
 */
public class RequestUseTimeGatewayFilterFactory extends AbstractGatewayFilterFactory<RequestUseTimeGatewayFilterFactory.Config> {

    private static final String REQUEST_TIME_BEGIN = "RequestTimeBegin";
    private static final String KEY = "withParams";


    public RequestUseTimeGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY);
    }

    //    @Override
    public GatewayFilter apply1(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange,
                                     GatewayFilterChain chain) {
                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                    if (startTime != null) {
                        ServerHttpRequest request = exchange.getRequest();
                        StringBuilder s = new StringBuilder("RequestUseTimeFilterFactory------->")
                                .append(request.getURI().getHost())
                                .append(request.getURI().getRawPath())
                                .append(": ")
                                .append(System.currentTimeMillis() - startTime)
                                .append(" ms");
                        if (config.isWithParams()) {
                            s.append("params: ").append(exchange.getRequest().getQueryParams());
                        }
                        System.out.println(s);
                    }
                }));
            }

        };
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());
            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                        Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                    if (startTime != null) {
                        ServerHttpRequest request = exchange.getRequest();
                        StringBuilder s = new StringBuilder("RequestUseTimeFilterFactory------->")
                                .append(request.getURI().getHost())
                                .append(request.getURI().getPort())
                                .append(request.getURI().getRawPath())
                                .append(": ")
                                .append(System.currentTimeMillis() - startTime)
                                .append(" ms");
                        if (config.isWithParams()) {
                            s.append("params: ").append(exchange.getRequest().getQueryParams());
                        }
                        System.out.println(s);
                    }
                    })
            );
        };
    }

    public static class Config {

        private boolean withParams;

        public boolean isWithParams() {
            return withParams;
        }

        public void setWithParams(boolean withParams) {
            this.withParams = withParams;
        }

    }
}
