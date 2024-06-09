package com.msclient.handler;

import com.msclient.dto.ClientWithProductsDTO;
import com.msclient.dto.CreateClientRequest;
import com.msclient.exceptions.BusinessException;
import com.msclient.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientHandler {

    private final ClientService clientService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<ClientWithProductsDTO> clients = clientService.findAll();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clients, ClientWithProductsDTO.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        Mono<ClientWithProductsDTO> client = clientService.findById(id);
        return client
                .flatMap(clientData -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(clientData))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getClientByCodigoUnico(ServerRequest request)  {
        String codigoUnico = request.pathVariable("codigoUnico");
        Mono<ClientWithProductsDTO> client = clientService.getClientByCodigoUnico(codigoUnico);
        return client
                .flatMap(clientData -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(clientData))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createClient(ServerRequest request) {
        Mono<CreateClientRequest> clientRequestMono = request.bodyToMono(CreateClientRequest.class);
        return clientRequestMono
                .flatMap(clientService::createClient)
                .flatMap(client -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(client));
    }

}
