package com.example.backend_sistemas_distribuidos.business.utils;

import com.example.backend_sistemas_distribuidos.business.entities.Usuario;
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

    private static final String SECRET_KEY="b7j1Dk8L3zQp0xT5v9FgJ6yZcW2LmA942073492340230472930472093742j423h4k2n34kn2j34k3490qN";

    public String getToken(Usuario usuario) {
        return getToken(new HashMap<>(), usuario);
    }
    private String getToken(Map<String, Object> extraClaims, Usuario usuario){
        return Jwts
                .builder()
                .claims(extraClaims)
                .claim("id",usuario.getId())
                .claim("cedula",usuario.getCedula())
                .claim("role",usuario.getRole())
                .subject(usuario.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+86400000 ))
                .signWith(getKey())
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
