package com.msproduct.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Product {

	@Hidden
	private Long id;

	@NotBlank(message = "El tipo de producto no debe estar en blanco")
	@Schema(description = "Tipo de producto", example = "Tarjeta de Débito")
	private String tipoProducto;

	@NotBlank(message = "El nombre del producto no debe estar en blanco")
	@Schema(description = "Nombre del producto", example = "Débito Platinum")
	private String nombreProducto;

	@NotNull(message = "El saldo no debe ser nulo")
	@Schema(description = "Saldo disponible en la tarjeta", example = "1500")
	private Double saldo;


}
