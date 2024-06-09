package com.msproduct.util;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class TraceIdFilter implements WebFilter {

    private static final String TRACE_ID_HEADER = "traceId";
    private static final String TRACE_ID_KEY = "traceId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String traceId = exchange.getRequest().getHeaders().getFirst(TRACE_ID_HEADER);

        if (traceId != null) {
            MDC.put(TRACE_ID_KEY, traceId);
        }

        return chain.filter(exchange)
                .doFinally(signalType -> MDC.remove(TRACE_ID_KEY));
    }
}

