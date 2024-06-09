package com.msclient.router;

import com.msclient.dto.CreateClientRequest;
import com.msclient.handler.ClientHandler;
import com.msclient.util.Constants;
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
                    path = "/api/clients/findall",
                    operation = @Operation(
                            summary = "Get all clients",
                            operationId = "findAllClients",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/clients/findbyid/{id}",
                    operation = @Operation(
                            summary = "Get client by ID",
                            operationId = "findClientById",
                            parameters = {
                                    @Parameter(name = "id", description = "Client ID", required = true, in = ParameterIn.PATH)
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "404", description = "Client Not Found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/clients/{codigoUnico}",
                    operation = @Operation(
                            summary = "Get client by CodigoUnico",
                            operationId = "getClientByCodigoUnico",
                            parameters = {
                                    @Parameter(name = "codigoUnico", description = "codigoUnico", required = true, in = ParameterIn.PATH)
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "404", description = "Client Not Found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/clients/create",
                    operation = @Operation(
                            summary = "Create a new client",
                            operationId = "createClient",
                            requestBody = @RequestBody(description = "Client to create", required = true,
                                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateClientRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Client Created", content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "400", description = "Bad Request")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> clientRoutes(ClientHandler handler)  {
        return RouterFunctions.route()
                .GET(Constants.FIND_ALL_PATH, handler::findAll)
                .GET(Constants.FIND_BY_ID_PATH, handler::findById)
                .GET(Constants.FIND_BY_CODIGO_UNICO_PATH, handler::getClientByCodigoUnico)
                .POST(Constants.CREATE_CLIENT_PATH, handler::createClient)
                .build();
    }

}

