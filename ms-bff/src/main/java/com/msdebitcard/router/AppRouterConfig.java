package com.msdebitcard.router;

import com.msdebitcard.handler.BffHandler;
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

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/bff/clients-products/{codigoUnico}",
                    operation = @Operation(
                            summary = "Get client with products by CodigoUnico",
                            operationId = "getClientWithProducts",
                            parameters = {
                                    @Parameter(name = "codigoUnico", description = "codigoUnico", required = true, in = ParameterIn.PATH)
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "404", description = "Client Not Found")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> bffRoutes(BffHandler handler) {
        return RouterFunctions.route()
                .GET("/api/bff/clients-products/{codigoUnico}", handler::getClientWithProducts)
                .build();
    }

}

