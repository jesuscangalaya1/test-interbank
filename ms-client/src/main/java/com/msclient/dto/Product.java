package com.msclient.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Product {

    @NotNull
    private String tipoProducto;

    @NotNull
    private String nombreProducto;

    @NotNull
    private Double saldo;

}
