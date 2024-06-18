package com.facturas.api_facturas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.facturas.api_facturas.pojo.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    
    User findByEmail(@Param("email") String email);
    
}
