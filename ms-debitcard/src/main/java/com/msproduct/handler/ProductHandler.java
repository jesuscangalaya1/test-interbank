package com.msproduct.handler;

import com.msproduct.dto.Product;
import com.msproduct.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductService debitCardService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<Product> debitCards = debitCardService.findAll();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(debitCards, Product.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        String traceId = request.headers().firstHeader("traceId");
        Mono<Product> debitCard = debitCardService.findById(id, traceId);

        return debitCard
                .flatMap(card -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("traceId", traceId)
                        .bodyValue(card))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createDebitCard(ServerRequest request) {
        Mono<Product> debitCardMono = request.bodyToMono(Product.class);
        return debitCardMono
                .flatMap(debitCardService::createDebitCard)
                .flatMap(card -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(card));
    }

}
