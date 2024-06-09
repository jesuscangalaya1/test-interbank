package com.msproduct.router;

import com.msproduct.dto.Product;
import com.msproduct.handler.ProductHandler;
import com.msproduct.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class AppRouterConfig {

    @Bean
    public WebProperties.Resources resources(){
        return new WebProperties.Resources();
    }


    @RouterOperations({
            @RouterOperation(
                    path = "/api/products/findall",
                    operation = @Operation(
                            summary = "Get all products",
                            operationId = "findAll",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/products/findbyid/{id}",
                    operation = @Operation(
                            summary = "Get product by ID",
                            operationId = "findById",
                            parameters = {
                                    @Parameter(name = "id", description = "Product ID", required = true, in = ParameterIn.PATH)
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "404", description = "Product not found"),
                                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/products/create",
                    operation = @Operation(
                            summary = "Create a new product",
                            operationId = "createDebitCard",
                            requestBody = @RequestBody(description = "Debit Card to create", required = true,
                                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
                            }
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> route(ProductHandler handler) {
        return RouterFunctions.route()
                .GET(Constants.FIND_ALL, handler::findAll)
                .GET(Constants.FIND_BY_ID, handler::findById)
                .POST(Constants.CREATE, handler::createDebitCard)
                .build();
    }

}

