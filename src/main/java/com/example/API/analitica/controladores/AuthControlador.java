package com.example.API.analitica.controladores;

import com.example.API.analitica.modelos.DTOs;
import com.example.API.analitica.servicios.AuthServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthControlador {

    @Autowired
    private AuthServicio authServicio;

    // POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<DTOs.ApiResponse> registrar(@RequestBody DTOs.RegisterRequest req) {
        DTOs.ApiResponse resp = authServicio.registrar(req);
        int status = resp.message.contains("exitosamente") ? 201 : 400;
        return ResponseEntity.status(status).body(resp);
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<DTOs.ApiResponse> login(@RequestBody DTOs.LoginRequest req) {
        DTOs.ApiResponse resp = authServicio.login(req);
        int status = resp.token != null ? 200 : 401;
        return ResponseEntity.status(status).body(resp);
    }

    // POST /api/auth/recover
    @PostMapping("/recover")
    public ResponseEntity<DTOs.ApiResponse> recuperar(@RequestBody DTOs.RecoverRequest req) {
        DTOs.ApiResponse resp = authServicio.recuperar(req);
        int status = resp.message.startsWith("Enlace") ? 200 : 404;
        return ResponseEntity.status(status).body(resp);
    }

    // GET /api/auth/health  — útil para verificar que el servidor está vivo
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth API operativa");
    }
}