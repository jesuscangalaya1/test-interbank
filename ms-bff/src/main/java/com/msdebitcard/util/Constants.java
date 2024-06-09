package com.msdebitcard.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.regex.Pattern;

@UtilityClass
public class Constants {

    // URLs
    public static final String MS_CLIENT_BASE_URL = "http://msclient:8080/api/clients/";

    // Headers
    public static final String HEADER_TRACE_ID = "traceId";

    // Log Messages
    public static final String LOG_SENDING_REQUEST_WITH_TRACE_ID = "Sending request with traceId: {}";

    // Error Codes
    public static final String CLIENT_NOT_FOUND = "CLIENT_NOT_FOUND";
    public static final String PRODUCT_SERVICE_ERROR = "PRODUCT_SERVICE_ERROR";

    // Patterns
    public static final Pattern CODIGO_UNICO_PATTERN = Pattern.compile("^[a-zA-Z0-9]{7}$");
}
