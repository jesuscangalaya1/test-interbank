package com.msclient.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.msclient.dto.Client;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.Flow;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, Long> {
    Mono<Client> findByCodigoUnico(String codigoUnico);


}
