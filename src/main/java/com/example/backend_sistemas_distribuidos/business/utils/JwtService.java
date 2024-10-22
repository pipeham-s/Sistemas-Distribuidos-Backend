package com.example.backend_sistemas_distribuidos.business.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;


@Service
public class JwtService {

    private static final String SECRET_KEY="UNJN2L3NI4234N2L34N23IL4N234IN23LK4N234jnbu42nni423lk4n2234hjb23kbu4234kl23b4ui23b4k23bu42k34b";

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
    private SecretKey getKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String getUsernameFromeToken(String token) {
        return getClaim(token, Claims::getSubject);

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromeToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private Claims getAllclaims(String token){
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }



    public <T> T getClaim (String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllclaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }
    private boolean isTokenExpired(String token ){
        return getExpiration(token).before(new Date());
    }

}
