package com.msproduct.service;

import com.msproduct.dto.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<Product> findAll();
    Mono<Product> findById(Long id, String traceId);
    Mono<Product> createDebitCard(Product debitCard);
}
