package com.larscheng.www;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;

/**
 * @author: larscheng
 * @date: 2020/4/21 下午6:51
 * @description:
 */
public class MyGateWayFilter implements GatewayFilter, Ordered {
    private static final String REQUEST_TIME_BEGIN = "RequestTimeBegin";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(REQUEST_TIME_BEGIN,System.currentTimeMillis());
        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
            if (startTime!=null){
                System.out.println(exchange.getRequest().getURI().getRawPath()+": "
                        +(System.currentTimeMillis()-startTime)+" ms");
            }
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
