package com.example.API.analitica.seguridad;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secreto;

    @Value("${jwt.expiracion-ms}")
    private long expiracionMs;

    // Genera la clave de firma a partir del secreto
    private Key obtenerClave() {
        return Keys.hmacShaKeyFor(secreto.getBytes());
    }

    /**
     * Genera un token JWT con el correo del usuario como "subject"
     * El token expira segun lo configurado en application.properties
     */
    public String generarToken(String correo) {
        return Jwts.builder()
                .setSubject(correo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiracionMs))
                .signWith(obtenerClave(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el correo (subject) del token
     */
    public String extraerCorreo(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(obtenerClave())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Valida que el token sea correcto y no haya expirado
     */
    public boolean esValido(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(obtenerClave())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
