package com.msdebitcard.service;

import com.msdebitcard.dto.ClientWithProductsDTO;
import com.msdebitcard.dto.Product;
import com.msdebitcard.exceptions.BusinessException;
import com.msdebitcard.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BffService {

    private final WebClient.Builder webClientBuilder;

    public Mono<ClientWithProductsDTO> getClientWithProducts(String codigoUnico) {
        String traceId = MDC.get(Constants.HEADER_TRACE_ID);
        if (traceId == null) {
            traceId = UUID.randomUUID().toString();
            MDC.put(Constants.HEADER_TRACE_ID, traceId);
        }
        log.info(Constants.LOG_SENDING_REQUEST_WITH_TRACE_ID, traceId);

        return webClientBuilder.build().get()
                .uri(Constants.MS_CLIENT_BASE_URL + "{codigoUnico}", codigoUnico)
                .header(Constants.HEADER_TRACE_ID, traceId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                        Mono.error(new BusinessException(
                                Constants.CLIENT_NOT_FOUND,
                                HttpStatus.NOT_FOUND,
                                "Client not found for codigoUnico: " + codigoUnico
                        ))
                )
                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                        Mono.error(new BusinessException(
                                Constants.PRODUCT_SERVICE_ERROR,
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Internal server error while fetching client for codigoUnico: " + codigoUnico
                        ))
                )
                .bodyToMono(ClientWithProductsDTO.class)
                .onErrorMap(WebClientResponseException.class, ex -> {
                    log.error("Error while calling client service: {}", ex.getMessage());
                    return new BusinessException(
                            Constants.PRODUCT_SERVICE_ERROR,
                            ex.getStatusCode(),
                            "Error while calling client service: " + ex.getResponseBodyAsString()
                    );
                });
    }
}

