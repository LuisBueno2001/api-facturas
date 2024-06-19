package com.facturas.api_facturas.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.facturas.api_facturas.security.Customers;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private Customers customers;
    private String username = null;
    Claims claims = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                if(request.getServletPath().matches("/user/login|user/forgotPassword|/user/signup")){
                    filterChain.doFilter(request, response);
                }else{
                    String autherizationHeader = request.getHeader("Authorization");
                    String token = null;

                    if (autherizationHeader != null && autherizationHeader.startsWith("Bearer ")) {
                        token = autherizationHeader.substring(7);
                        username = jwtUtil.extractUsername(token);
                        claims = jwtUtil.extractAllClaims(token);
                    }

                    if(username != null && SecurityContextHolder.getContext().getAuthentication() == null ){
                        UserDetails userDetails = customers.loadUserByUsername(username);
                        if (jwtUtil.validateToken(token, userDetails)) {
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            new WebAuthenticationDetailsSource().buildDetails(request);
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        }
                    }
                    filterChain.doFilter(request, response);
                }
            }
            public Boolean isAdmin(){
                return "admin".equalsIgnoreCase((String) claims.get("rol"));
            }
            public Boolean isUser(){
                return "user".equalsIgnoreCase((String) claims.get("rol"));
            }
            public String getCurrentUser(){
                return username;
            }
}
