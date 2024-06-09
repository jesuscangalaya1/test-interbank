package com.msproduct.service.iml;

import com.msproduct.dao.ProductRepository;
import com.msproduct.dto.Product;
import com.msproduct.exceptions.BusinessException;
import com.msproduct.service.ProductService;
import com.msproduct.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

	private final ProductRepository debitCardRepository;
	private final SequenceGeneratorService sequenceGeneratorService;


	@Override
	public Flux<Product> findAll() {
		return debitCardRepository.findAll()
				.switchIfEmpty(Flux.error(new BusinessException(
						Constants.DEBIT_CARDS_NOT_FOUND,
						Constants.NOT_FOUND_STATUS,
						Constants.NO_DEBIT_CARDS_FOUND_MESSAGE
				)));
	}

	@Override
	public Mono<Product> findById(Long id, String traceId) {
		if (traceId == null) {
			traceId = UUID.randomUUID().toString();
		}
		MDC.put("traceId", traceId);
		log.info("Received traceId: {}", traceId);
		return debitCardRepository.findById(id)
				.switchIfEmpty(Mono.error(new BusinessException(
						Constants.DEBIT_CARD_NOT_FOUND,
						Constants.NOT_FOUND_STATUS,
						Constants.DEBIT_CARD_NOT_FOUND_MESSAGE + id
				)));
	}


	@Override
	public Mono<Product> createDebitCard(Product debitCard) {
		return sequenceGeneratorService.generateSequence(Product.class.getSimpleName())
				.flatMap(sequence -> {
					debitCard.setId(sequence);
					return debitCardRepository.insert(debitCard);
				})
				.onErrorMap(e -> new BusinessException(
						Constants.DEBIT_CARD_CREATION_FAILED,
						Constants.INTERNAL_SERVER_ERROR_STATUS,
						Constants.DEBIT_CARD_CREATION_FAILED_MESSAGE + e.getMessage()
				));
	}
	
}
