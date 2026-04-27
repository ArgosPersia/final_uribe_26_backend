package com.example.API.analitica.controladores;

import com.example.API.analitica.modelos.DTOs.LoginRequest;
import com.example.API.analitica.modelos.DTOs.LoginResponse;
import com.example.API.analitica.modelos.DTOs.RegisterRequest;
import com.example.API.analitica.modelos.DTOs.MensajeResponse;
import com.example.API.analitica.modelos.DTOs.RecoverRequest;
import com.example.API.analitica.servicios.AuthServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthControlador {

    private final AuthServicio authServicio;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        LoginResponse respuesta = authServicio.login(req);
        if (respuesta.getToken() == null) {
            return ResponseEntity.status(401).body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/register")
    public ResponseEntity<MensajeResponse> registrar(@RequestBody RegisterRequest req) {
        MensajeResponse respuesta = authServicio.registrar(req);
        if (respuesta.getMessage().contains("ya existe")) {
            return ResponseEntity.status(409).body(respuesta);
        }
        return ResponseEntity.status(201).body(respuesta);
    }

    @PostMapping("/recover")
    public ResponseEntity<MensajeResponse> recuperar(@RequestBody RecoverRequest req) {
        MensajeResponse respuesta = authServicio.recuperar(req);
        if (respuesta.getMessage().contains("No se encontró")) {
            return ResponseEntity.status(404).body(respuesta);
        }
        return ResponseEntity.ok(respuesta);
    }
}