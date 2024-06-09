package com.msclient.mapper;

import com.msclient.dto.Client;
import com.msclient.dto.ClientWithProductsDTO;
import com.msclient.dto.CreateClientRequest;
import com.msclient.dto.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "productosFinancieros", source = "product", qualifiedByName = "productToProductList")
    ClientWithProductsDTO toClientWithProductsDTO(Client client, Product product);

    Client toClient(CreateClientRequest createClientRequest);

    @Named("productToProductList")
    default List<Product> productToProductList(Product product) {
        return Collections.singletonList(product);
    }

}
