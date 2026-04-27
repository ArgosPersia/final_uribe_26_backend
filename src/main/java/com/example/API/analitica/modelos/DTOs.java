package com.example.API.analitica.modelos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Clases DTO (Data Transfer Object)
 * Son los objetos que viajan entre el frontend y el backend
 * Separados del modelo para no exponer la entidad directamente
 */
public class DTOs {

    // ─── AUTH ────────────────────────────────────────────────────

    /** Lo que manda el front al hacer POST /api/auth/login */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String identifier; // puede ser correo o nombre de usuario
        private String password;
    }

    /** Lo que manda el front al hacer POST /api/auth/register */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        private String nombre;
        private String correo;
        private String password;
    }

    /** Lo que manda el front al hacer POST /api/auth/recover */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecoverRequest {
        private String correo;
    }

    /** Lo que devuelve el back despues de un login exitoso */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private String token;
        private String nombre;
        private String correo;
        private String message;
    }

    /** Respuesta generica para mensajes simples */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeResponse {
        private String message;
    }

    // ─── VENTAS ──────────────────────────────────────────────────

    /** Lo que manda el front al registrar una venta */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VentaRequest {
        private String producto;
        private Integer precioUnitario;
        private String talla;
        private Integer cantidad;
        private String vendedor;
        private Double total;
        private String fecha; // viene como String "2026-01-15", se parsea en el servicio
    }

    /** Lo que devuelve el back con los datos de la venta guardada */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VentaResponse {
        private Long id;
        private String producto;
        private Integer precioUnitario;
        private String talla;
        private Integer cantidad;
        private String vendedor;
        private Double total;
        private String fecha;
        private String estado;
        private String message;
    }
}
