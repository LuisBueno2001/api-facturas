package com.facturas.api_facturas.security;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.facturas.api_facturas.pojo.User;
import com.facturas.api_facturas.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Customers implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    private User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Dentro del loadUserByUsername {}", username);
        userDetail = userRepository.findByEmail(username);
        
        if(!Objects.isNull(userDetail)){
            return new org.springframework.security.core.userdetails.User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

    public User getUserDetail(){
        return userDetail;
    }
}
