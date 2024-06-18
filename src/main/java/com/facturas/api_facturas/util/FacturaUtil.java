package com.facturas.api_facturas.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FacturaUtil {

    private FacturaUtil() {

    }

    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus httpStatus) {
        return new ResponseEntity<String>("Mensaje: " + message, httpStatus);
    }

}