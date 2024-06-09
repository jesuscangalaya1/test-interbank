package com.msclient;

import com.msclient.dto.Client;
import com.msclient.dto.CreateClientRequest;
import com.msclient.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MsClientApplication implements CommandLineRunner {

    @Autowired
    private ClientRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(MsClientApplication.class, args);
    }


    @Override
    public void run(String... args) {
        Client client1 = new Client();
        client1.setCodigoUnico("abc1234");
        client1.setNombres("Juan");
        client1.setApellidos("Pérez");
        client1.setTipoDocumento("DNI");
        client1.setNumeroDocumento("70124126");
        client1.setProductoId(1L);

        Client client2 = new Client();
        client2.setCodigoUnico("def5678");
        client2.setNombres("Maria");
        client2.setApellidos("Gomez");
        client2.setTipoDocumento("DNI");
        client2.setNumeroDocumento("70124127");
        client2.setProductoId(2L);

        Client client3 = new Client();
        client2.setCodigoUnico("1234567");
        client2.setNombres("Jesus");
        client2.setApellidos("Cangalaya");
        client2.setTipoDocumento("DNI");
        client2.setNumeroDocumento("70124123");
        client2.setProductoId(2L);
        repository.insert(List.of(client1, client2,client3)).subscribe();
    }
}
