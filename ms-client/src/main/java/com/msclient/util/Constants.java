package com.msclient.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.regex.Pattern;

@UtilityClass
public class Constants {
    public static final String CLIENT_NOT_FOUND = "CLIENT_NOT_FOUND";
    public static final String PRODUCT_NOT_FOUND = "PRODUCT_NOT_FOUND";
    public static final String PRODUCT_SERVICE_ERROR = "PRODUCT_SERVICE_ERROR";
    public static final String INVALID_CODIGO_UNICO = "INVALID_CODIGO_UNICO";
    public static final String CODIGO_UNICO_EXISTS = "CODIGO_UNICO_EXISTS";

    public static final String CLIENT_NOT_FOUND_MESSAGE = "Client not found with id: ";
    public static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found for client with id: ";
    public static final String PRODUCT_SERVICE_ERROR_MESSAGE = "Error occurred while calling product service for client with id: ";
    public static final String INVALID_CODIGO_UNICO_MESSAGE = "El codigoUnico debe tener exactamente 7 caracteres alfanuméricos sin acentos.";
    public static final String CODIGO_UNICO_EXISTS_MESSAGE = "El codigoUnico ya existe.";

    public static final String MS_PRODUCTS_BASE_URL = "http://msdebitcard:8090/api/products";
    public static final String FIND_BY_ID_URI = "http://msdebitcard:8090/api/products/findbyid/{id}";

    public static final String API_BASE_PATH = "/api/clients";
    public static final String FIND_ALL_PATH = API_BASE_PATH + "/findall";
    public static final String FIND_BY_ID_PATH = API_BASE_PATH + "/findbyid/{id}";
    public static final String FIND_BY_CODIGO_UNICO_PATH = API_BASE_PATH + "/{codigoUnico}";
    public static final String CREATE_CLIENT_PATH = API_BASE_PATH + "/create";

    public static final HttpStatus NOT_FOUND_STATUS = HttpStatus.NOT_FOUND;
    public static final HttpStatus INTERNAL_SERVER_ERROR_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    public static final HttpStatus BAD_REQUEST_STATUS = HttpStatus.BAD_REQUEST;
    public static final HttpStatus CONFLICT_STATUS = HttpStatus.CONFLICT;

    public static final Pattern CODIGO_UNICO_PATTERN = Pattern.compile("^[a-zA-Z0-9]{7}$");
}

