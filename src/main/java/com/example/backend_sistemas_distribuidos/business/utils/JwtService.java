package com.example.backend_sistemas_distribuidos.business.utils;

import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import javax.crypto.SecretKey;

@Service
public class JwtService {

    private static final String SECRET_KEY="UNJN2L3NI4234N2L34N23IL4N234IN23LK4N 234";
    public String getToken(UserDetails usuario) {
        return getToken(new HashMap<>(), usuario);
    }
    private String getToken(Map<String, Object> extraClaims, UserDetails usuario){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+86400000 ))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact(); 


    }


}
