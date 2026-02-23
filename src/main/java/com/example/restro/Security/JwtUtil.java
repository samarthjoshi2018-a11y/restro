package com.example.restro.Security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.example.restro.Security.services.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {


    String secretkey="mysecret569dkfjidjfiejifijeiffji";
    SecretKey key = Keys.hmacShaKeyFor(secretkey.getBytes());
        

        
    public String generateToken(CustomUserDetails userdetails){

        Map<String,Object> claims=new HashMap<>();

        claims.put("userType", userdetails.getUserType());

        return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userdetails.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                    .signWith(key,SignatureAlgorithm.HS256)
                    .compact();

    }
    
    public String extractUserType(String token){
        Claims c= extractallclaims(token);
        return c.get("userType",String.class);

    }

    public Boolean isTokenExpired(String token){
        Claims c=extractallclaims(token);
        Date d=c.getExpiration();
        return d.before(new Date());
    }

    public String extractEmail(String token){
        Claims c=extractallclaims(token);
        return  c.getSubject();
    }
    public Claims extractallclaims(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Boolean validateToken(String token,CustomUserDetails ud){
        String email=extractEmail(token);
        return (ud.getUsername().equals(email) && !isTokenExpired(token));
    }

}
