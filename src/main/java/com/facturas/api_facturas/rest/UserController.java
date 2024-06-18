package com.facturas.api_facturas.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturas.api_facturas.constantes.FacturaConstantes;
import com.facturas.api_facturas.service.UserService;
import com.facturas.api_facturas.util.FacturaUtil;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/signup")
    public ResponseEntity<String> registrarUser(@RequestBody(required = true)Map<String, String> requestMap){
        try {
            return userService.signup(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtil.getResponseEntity(FacturaConstantes.SOMETING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
