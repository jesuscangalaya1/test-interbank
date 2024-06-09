package com.msproduct;

import com.msproduct.dao.ProductRepository;
import com.msproduct.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringbootWebfluxDemoApplication {

	@Autowired
	private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWebfluxDemoApplication.class, args);
	}

	@PostConstruct
	public void initProducts() {
		Product product1 = new Product();
		product1.setTipoProducto("Tarjeta de Débito");
		product1.setNombreProducto("Débito Platinum");
		product1.setSaldo(1500.0);

		Product product2 = new Product();
		product2.setTipoProducto("Tarjeta de Crédito");
		product2.setNombreProducto("Crédito Gold");
		product2.setSaldo(3000.0);

		Product product3= new Product();
		product2.setTipoProducto("Tarjeta de Crédito");
		product2.setNombreProducto("Ahorro");
		product2.setSaldo(2000.0);

		Flux.just(product1, product2, product3)
				.flatMap(product -> productRepository.save(product))
				.subscribe();
	}

}
