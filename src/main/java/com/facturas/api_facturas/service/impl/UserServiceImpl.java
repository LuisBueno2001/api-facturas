package com.facturas.api_facturas.service.impl;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.facturas.api_facturas.constantes.FacturaConstantes;
import com.facturas.api_facturas.pojo.User;
import com.facturas.api_facturas.repository.UserRepository;
import com.facturas.api_facturas.service.UserService;
import com.facturas.api_facturas.util.FacturaUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestMap) {
        log.info("Registro de un Usuario {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userRepository.findByEmail(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userRepository.save(getUserFromMap(requestMap));
                    return FacturaUtil.getResponseEntity("Usuario registrado con exito", HttpStatus.CREATED);
                } else {
                    return FacturaUtil.getResponseEntity("El usuario con este email ya existe", HttpStatus.BAD_REQUEST);
                }
            }else{
                return FacturaUtil.getResponseEntity(FacturaConstantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return FacturaUtil.getResponseEntity(FacturaConstantes.INVALID_DATA, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("phoneNumber") && requestMap.containsKey("email") && requestMap.containsKey("password")) {
            return true;
        }else{  
        return false;
        }
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setPhoneNumber(requestMap.get("phoneNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRol("user");
        return user;
    }
}
