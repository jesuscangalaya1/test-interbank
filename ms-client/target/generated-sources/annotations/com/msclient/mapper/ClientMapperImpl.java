package com.msclient.mapper;

import com.msclient.dto.Client;
import com.msclient.dto.ClientWithProductsDTO;
import com.msclient.dto.CreateClientRequest;
import com.msclient.dto.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-09T16:33:33-0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientWithProductsDTO toClientWithProductsDTO(Client client, Product product) {
        if ( client == null && product == null ) {
            return null;
        }

        ClientWithProductsDTO clientWithProductsDTO = new ClientWithProductsDTO();

        if ( client != null ) {
            clientWithProductsDTO.setId( client.getId() );
            clientWithProductsDTO.setCodigoUnico( client.getCodigoUnico() );
            clientWithProductsDTO.setNombres( client.getNombres() );
            clientWithProductsDTO.setApellidos( client.getApellidos() );
            clientWithProductsDTO.setTipoDocumento( client.getTipoDocumento() );
            clientWithProductsDTO.setNumeroDocumento( client.getNumeroDocumento() );
        }
        clientWithProductsDTO.setProductosFinancieros( productToProductList( product ) );

        return clientWithProductsDTO;
    }

    @Override
    public Client toClient(CreateClientRequest createClientRequest) {
        if ( createClientRequest == null ) {
            return null;
        }

        Client.ClientBuilder client = Client.builder();

        client.codigoUnico( createClientRequest.getCodigoUnico() );
        client.nombres( createClientRequest.getNombres() );
        client.apellidos( createClientRequest.getApellidos() );
        client.tipoDocumento( createClientRequest.getTipoDocumento() );
        client.numeroDocumento( createClientRequest.getNumeroDocumento() );
        client.productoId( createClientRequest.getProductoId() );

        return client.build();
    }
}
