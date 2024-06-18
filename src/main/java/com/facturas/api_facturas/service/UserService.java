package com.facturas.api_facturas.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<String> signup(Map<String,String> requestMap);
    
}
