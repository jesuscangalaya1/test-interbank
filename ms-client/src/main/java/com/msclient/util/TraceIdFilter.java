package com.msclient.util;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class TraceIdFilter implements WebFilter {

    private static final String TRACE_ID_HEADER = "traceId";
    private static final String TRACE_ID_KEY = "traceId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String traceId = exchange.getRequest().getHeaders().getFirst("traceId");
        if (traceId != null) {
            MDC.put("traceId", traceId);
        } else {
            traceId = UUID.randomUUID().toString();
            MDC.put("traceId", traceId);
        }
        return chain.filter(exchange)
                .doOnSuccess(aVoid -> MDC.remove("traceId"));
    }
}

