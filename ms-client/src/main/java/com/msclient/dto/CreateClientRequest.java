package com.msclient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class CreateClientRequest {
    @NotBlank(message = "El código único no debe estar en blanco")
    @Size(max = 7, min = 7, message = "El código único debe tener exactamente {max} caracteres.")
    @Schema(description = "Código único del cliente", example = "abc1234")
    private String codigoUnico;

    @NotBlank(message = "Los nombres no deben estar en blanco")
    @Schema(description = "Nombres del cliente", example = "Juan")
    private String nombres;

    @NotBlank(message = "Los apellidos no deben estar en blanco")
    @Schema(description = "Apellidos del cliente", example = "Pérez")
    private String apellidos;

    @NotBlank(message = "El tipo de documento no debe estar en blanco")
    @Schema(description = "Tipo de documento del cliente", example = "DNI")
    private String tipoDocumento;

    @NotBlank(message = "El número de documento no debe estar en blanco")
    @Schema(description = "Número de documento del cliente", example = "70124126")
    private String numeroDocumento;

    @NotNull(message = "El ID del producto no debe ser nulo")
    @Schema(description = "ID del producto asociado al cliente", example = "1")
    private Long productoId;
}

