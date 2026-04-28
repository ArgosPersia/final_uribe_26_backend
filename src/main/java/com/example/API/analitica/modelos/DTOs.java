package com.example.API.analitica.modelos;

// DTO para autenticación

public class DTOs {

    // Request para login
    public static class LoginRequest {
        public String identifier;
        public String password;
    }

    // Request para registro
    public static class RegisterRequest {
        public String nombre;
        public String correo;
        public String password;
    }

    // Request para recuperar contraseña
    public static class RecoverRequest {
        public String correo;
    }

    // Respuesta genérica de la API
    public static class ApiResponse {
        public String message;
        public String token;
        public String nombre;

        public ApiResponse(String message) {
            this.message = message;
        }

        public ApiResponse(String message, String token, String nombre) {
            this.message = message;
            this.token   = token;
            this.nombre  = nombre;
        }
    }
}