package com.msdebitcard.handler;

import com.msdebitcard.service.BffService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BffHandler {

    private final BffService bffService;

    public Mono<ServerResponse> getClientWithProducts(ServerRequest request) {
        String codigoUnico = request.pathVariable("codigoUnico");
        return bffService.getClientWithProducts(codigoUnico)
                .flatMap(clientWithProductsDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(clientWithProductsDTO))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}

