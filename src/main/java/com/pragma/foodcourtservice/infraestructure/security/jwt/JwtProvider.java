package com.pragma.foodcourtservice.infraestructure.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import java.util.Date;

@Slf4j
public class JwtProvider {
    private static final String JWT_SECRET_KEY = "clavelosuficientementelargaparaquehmac256lasoporte";
    private JwtProvider(){
        throw new IllegalStateException("Utility Class");
    }

    public static boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch(Exception e){
            log.error(e.getMessage());
            return false;
        }
    }
    public static String getSubjectFromToken(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
    public static String getRolFromToken(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("rol", String.class);
        }catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
    public static Long getIdFromToken(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("id", Long.class);
        }catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}