package com.msclient.service;

import com.msclient.dto.Client;
import com.msclient.dto.ClientWithProductsDTO;
import com.msclient.dto.CreateClientRequest;
import com.msclient.exceptions.BusinessException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    Mono<ClientWithProductsDTO> findById(Long id);

    Flux<ClientWithProductsDTO> findAll();

    Mono<Client> createClient(CreateClientRequest createClientRequest);

    Mono<ClientWithProductsDTO> getClientByCodigoUnico(String encodedCodigoUnico) ;


}
