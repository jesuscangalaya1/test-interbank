package com.msclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.MDC;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder().filter((request, next) -> {
            String traceId = MDC.get("traceId");
            if (traceId != null) {
                request.headers().add("traceId", traceId);
            }
            return next.exchange(request);
        });
    }
}

