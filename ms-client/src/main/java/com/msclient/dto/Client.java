package com.msclient.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Encrypted;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {

	private Long id;

	@NotNull
	private String codigoUnico;

	@NotNull
	private String nombres;

	@NotNull
	private String apellidos;

	@NotNull
	private String tipoDocumento;

	@NotNull
	private String numeroDocumento;

	@NotNull
	private Long productoId;
}
