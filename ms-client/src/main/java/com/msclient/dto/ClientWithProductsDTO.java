package com.msclient.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClientWithProductsDTO {

    private Long id;
    private String codigoUnico;
    private String nombres;
    private String apellidos;
    private String tipoDocumento;
    private String numeroDocumento;
    private List<Product> productosFinancieros;
}
