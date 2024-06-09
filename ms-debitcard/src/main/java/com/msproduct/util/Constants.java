package com.msproduct.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class Constants {
    public static final String DEBIT_CARDS_NOT_FOUND = "DEBIT_CARDS_NOT_FOUND";
    public static final String DEBIT_CARD_NOT_FOUND = "DEBIT_CARD_NOT_FOUND";
    public static final String DEBIT_CARD_CREATION_FAILED = "DEBIT_CARD_CREATION_FAILED";

    public static final String NO_DEBIT_CARDS_FOUND_MESSAGE = "No debit cards found.";
    public static final String DEBIT_CARD_NOT_FOUND_MESSAGE = "Debit card not found with id: ";
    public static final String DEBIT_CARD_CREATION_FAILED_MESSAGE = "Failed to create debit card: ";

    public static final HttpStatus NOT_FOUND_STATUS = HttpStatus.NOT_FOUND;
    public static final HttpStatus INTERNAL_SERVER_ERROR_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public static final String BASE_PATH = "/api/products";
    public static final String FIND_ALL = BASE_PATH + "/findall";
    public static final String FIND_BY_ID = BASE_PATH + "/findbyid/{id}";
    public static final String CREATE = BASE_PATH + "/create";

}
