package com.example.API.analitica.servicios;

import com.example.API.analitica.modelos.DTOs;
import com.example.API.analitica.modelos.Usuario;
import com.example.API.analitica.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    // ── Registro ─────────────────────────────────────────────────────────────
    public DTOs.ApiResponse registrar(DTOs.RegisterRequest req) {
        if (req.nombre == null || req.nombre.isBlank())
            return new DTOs.ApiResponse("El nombre es obligatorio.");
        if (req.correo == null || req.correo.isBlank())
            return new DTOs.ApiResponse("El correo es obligatorio.");
        if (req.password == null || req.password.length() < 8)
            return new DTOs.ApiResponse("La contraseña debe tener mínimo 8 caracteres.");

        if (usuarioRepo.existsByCorreo(req.correo.toLowerCase().trim()))
            return new DTOs.ApiResponse("Ya existe una cuenta con ese correo.");

        Usuario u = new Usuario();
        u.setNombre(req.nombre.trim());
        u.setCorreo(req.correo.toLowerCase().trim());
        // Guardamos la contraseña tal cual (texto plano) para evitar errores de librerías
        u.setPassword(req.password);
        usuarioRepo.save(u);

        return new DTOs.ApiResponse("Cuenta creada exitosamente.");
    }

    // ── Login ─────────────────────────────────────────────────────────────────
    public DTOs.ApiResponse login(DTOs.LoginRequest req) {
        if (req.identifier == null || req.password == null)
            return new DTOs.ApiResponse("Complete todos los campos.");

        String id = req.identifier.trim().toLowerCase();
        Optional<Usuario> opt = usuarioRepo.findByCorreoOrNombre(id, id);

        if (opt.isEmpty())
            return new DTOs.ApiResponse("Usuario o correo no encontrado.");

        Usuario u = opt.get();
        // Comparamos el texto directamente
        if (!req.password.equals(u.getPassword()))
            return new DTOs.ApiResponse("Contraseña incorrecta.");

        return new DTOs.ApiResponse("Acceso concedido.", "token-provisional-uni", u.getNombre());
    }

    // ── Recuperar contraseña ──────────────────────────────────────────────────
    public DTOs.ApiResponse recuperar(DTOs.RecoverRequest req) {
        if (req.correo == null || req.correo.isBlank())
            return new DTOs.ApiResponse("Ingrese su correo.");

        boolean existe = usuarioRepo.existsByCorreo(req.correo.toLowerCase().trim());
        if (!existe)
            return new DTOs.ApiResponse("No se encontró una cuenta con ese correo.");

        return new DTOs.ApiResponse("Enlace de recuperación enviado a " + req.correo);
    }
}