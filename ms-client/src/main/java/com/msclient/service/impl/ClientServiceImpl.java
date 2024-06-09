package com.msclient.service.impl;

import com.msclient.dto.ClientWithProductsDTO;
import com.msclient.dto.CreateClientRequest;
import com.msclient.dto.Product;
import com.msclient.exceptions.BusinessException;
import com.msclient.mapper.ClientMapper;
import com.msclient.repository.ClientRepository;
import com.msclient.service.ClientService;
import com.msclient.service.SequenceGeneratorService;
import com.msclient.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.msclient.dto.Client;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

	private final WebClient webMsProducts = WebClient.create("http://msdebitcard:8090/api/products");
	private final WebClient.Builder webClientBuilder;
	private final ClientRepository clientRepository;
	private final SequenceGeneratorService sequenceGeneratorService;
	private final ClientMapper clientMapper;
	private static final Pattern CODIGO_UNICO_PATTERN = Pattern.compile("^[a-zA-Z0-9]{7}$");


	@Override
	public Mono<ClientWithProductsDTO> findById(Long id) {
		return clientRepository.findById(id)
				.switchIfEmpty(Mono.error(new BusinessException(
						Constants.CLIENT_NOT_FOUND,
						Constants.NOT_FOUND_STATUS,
						Constants.CLIENT_NOT_FOUND_MESSAGE + id)))
				.flatMap(client -> webMsProducts.get()
						.uri(Constants.FIND_BY_ID_URI, client.getId())
						.retrieve()
						.onStatus(HttpStatus::is4xxClientError, clientResponse ->
								Mono.error(new BusinessException(
										Constants.PRODUCT_NOT_FOUND,
										Constants.NOT_FOUND_STATUS,
										Constants.PRODUCT_NOT_FOUND_MESSAGE + id)))
						.onStatus(HttpStatus::is5xxServerError, clientResponse ->
								Mono.error(new BusinessException(
										Constants.PRODUCT_SERVICE_ERROR,
										Constants.INTERNAL_SERVER_ERROR_STATUS,
										Constants.PRODUCT_SERVICE_ERROR_MESSAGE + id)))
						.bodyToMono(Product.class)
						.map(product -> clientMapper.toClientWithProductsDTO(client, product)));
	}

	@Override
	public Flux<ClientWithProductsDTO> findAll() {
		return clientRepository.findAll()
				.flatMap(client -> webMsProducts.get()
						.uri(Constants.FIND_BY_ID_URI, client.getProductoId())
						.retrieve()
						.onStatus(HttpStatus::is4xxClientError, clientResponse ->
								Mono.error(new BusinessException(
										Constants.PRODUCT_NOT_FOUND,
										Constants.NOT_FOUND_STATUS,
										Constants.PRODUCT_NOT_FOUND_MESSAGE + client.getId())))
						.onStatus(HttpStatus::is5xxServerError, clientResponse ->
								Mono.error(new BusinessException(
										Constants.PRODUCT_SERVICE_ERROR,
										Constants.INTERNAL_SERVER_ERROR_STATUS,
										Constants.PRODUCT_SERVICE_ERROR_MESSAGE + client.getId())))
						.bodyToMono(Product.class)
						.map(product -> clientMapper.toClientWithProductsDTO(client, product)));
	}


	@Override
	public Mono<Client> createClient(CreateClientRequest createClientRequest) {
		if (!isValidCodigoUnico(createClientRequest.getCodigoUnico())) {
			return Mono.error(new BusinessException(
					Constants.INVALID_CODIGO_UNICO,
					Constants.BAD_REQUEST_STATUS,
					Constants.INVALID_CODIGO_UNICO_MESSAGE
			));
		}

		String encodedCodigoUnico = Base64.getEncoder().encodeToString(
				createClientRequest.getCodigoUnico().getBytes(StandardCharsets.UTF_8)
		);

		return clientRepository.findByCodigoUnico(encodedCodigoUnico)
				.flatMap(existingClient -> Mono.error(new BusinessException(
						Constants.CODIGO_UNICO_EXISTS,
						Constants.CONFLICT_STATUS,
						Constants.CODIGO_UNICO_EXISTS_MESSAGE
				)))
				.switchIfEmpty(
						webMsProducts.get()
								.uri(Constants.FIND_BY_ID_URI, createClientRequest.getProductoId())
								.retrieve()
								.onStatus(HttpStatus::is4xxClientError, clientResponse ->
										Mono.error(new BusinessException(
												Constants.PRODUCT_NOT_FOUND,
												Constants.NOT_FOUND_STATUS,
												Constants.PRODUCT_NOT_FOUND_MESSAGE + createClientRequest.getProductoId()
										)))
								.onStatus(HttpStatus::is5xxServerError, clientResponse ->
										Mono.error(new BusinessException(
												Constants.PRODUCT_SERVICE_ERROR,
												Constants.INTERNAL_SERVER_ERROR_STATUS,
												Constants.PRODUCT_SERVICE_ERROR_MESSAGE + createClientRequest.getProductoId()
										)))
								.bodyToMono(Product.class)
								.then(sequenceGeneratorService.generateSequence(Client.class.getSimpleName()))
								.flatMap(sequence -> {
									Client client = clientMapper.toClient(createClientRequest);
									client.setId(sequence);
									client.setCodigoUnico(encodedCodigoUnico);
									return clientRepository.insert(client);
								})
				).cast(Client.class);
	}





	@Override
	public Mono<ClientWithProductsDTO> getClientByCodigoUnico(String encodedCodigoUnico) {
		log.info("Received encodedCodigoUnico: {}", encodedCodigoUnico);

		String decodedCodigoUnico;
		try {
			decodedCodigoUnico = decodeCodigoUnico(encodedCodigoUnico);
		} catch (BusinessException e) {
			return Mono.error(e);
		}

		if (!isValidCodigoUnico(decodedCodigoUnico)) {
			return Mono.error(new BusinessException(
					Constants.INVALID_CODIGO_UNICO,
					Constants.BAD_REQUEST_STATUS,
					Constants.INVALID_CODIGO_UNICO_MESSAGE
			));
		}

		log.info("Decoded codigoUnico: {}", decodedCodigoUnico);

		return clientRepository.findByCodigoUnico(encodedCodigoUnico)
				.switchIfEmpty(Mono.error(new BusinessException(
						Constants.CLIENT_NOT_FOUND,
						Constants.NOT_FOUND_STATUS,
						Constants.CLIENT_NOT_FOUND_MESSAGE + decodedCodigoUnico
				)))
				.flatMap(client -> {
					log.info("Found client: {}", client);
					String traceId = MDC.get("traceId");

					return webClientBuilder.build().get()
							.uri(Constants.FIND_BY_ID_URI, client.getProductoId())
							.header("traceId", traceId)
							.retrieve()
							.onStatus(HttpStatus::is4xxClientError, clientResponse ->
									Mono.error(new BusinessException(
											Constants.PRODUCT_NOT_FOUND,
											Constants.NOT_FOUND_STATUS,
											Constants.PRODUCT_NOT_FOUND_MESSAGE + decodedCodigoUnico
									)))
							.onStatus(HttpStatus::is5xxServerError, clientResponse ->
									Mono.error(new BusinessException(
											Constants.PRODUCT_SERVICE_ERROR,
											Constants.INTERNAL_SERVER_ERROR_STATUS,
											Constants.PRODUCT_SERVICE_ERROR_MESSAGE + decodedCodigoUnico
									)))
							.bodyToMono(Product.class)
							.map(product -> clientMapper.toClientWithProductsDTO(client, product));
				});
	}







	private boolean isValidCodigoUnico(String codigoUnico) {
		return codigoUnico != null && CODIGO_UNICO_PATTERN.matcher(codigoUnico).matches();
	}

	private String decodeCodigoUnico(String encodedCodigoUnico) throws BusinessException {
		try {
			return new String(Base64.getDecoder().decode(encodedCodigoUnico), StandardCharsets.UTF_8);
		} catch (IllegalArgumentException e) {
			throw new BusinessException(
					Constants.INVALID_CODIGO_UNICO,
					Constants.BAD_REQUEST_STATUS,
					Constants.INVALID_CODIGO_UNICO_MESSAGE
			);
		}
	}


}
