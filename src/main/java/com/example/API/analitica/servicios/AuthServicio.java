package com.example.API.analitica.servicios;

import com.example.API.analitica.modelos.DTOs.LoginRequest;
import com.example.API.analitica.modelos.DTOs.LoginResponse;
import com.example.API.analitica.modelos.DTOs.RegisterRequest;
import com.example.API.analitica.modelos.DTOs.MensajeResponse;
import com.example.API.analitica.modelos.DTOs.RecoverRequest;
import com.example.API.analitica.modelos.Usuario;
import com.example.API.analitica.repositorios.UsuarioRepositorio;
import com.example.API.analitica.seguridad.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServicio {

    private final UsuarioRepositorio usuarioRepo;
    private final JwtUtil jwtUtil;

    /**
     * LOGIN
     * El front manda { identifier, password }
     * identifier puede ser correo o nombre de usuario
     */
    public LoginResponse login(LoginRequest req) {

        // Buscar primero por correo, luego por nombre
        Optional<Usuario> encontrado = usuarioRepo.findByCorreo(req.getIdentifier());
        if (encontrado.isEmpty()) {
            encontrado = usuarioRepo.findByNombre(req.getIdentifier());
        }

        // Si no existe el usuario
        if (encontrado.isEmpty()) {
            return new LoginResponse(null, null, null, "Credenciales incorrectas.");
        }

        Usuario usuario = encontrado.get();

        // Verificar contrasena
        // (En proyecto universitario comparacion directa; en produccion usaria BCrypt)
        if (!usuario.getPassword().equals(req.getPassword())) {
            return new LoginResponse(null, null, null, "Credenciales incorrectas.");
        }

        // Generar token JWT
        String token = jwtUtil.generarToken(usuario.getCorreo());

        return new LoginResponse(token, usuario.getNombre(), usuario.getCorreo(), "Login exitoso.");
    }

    /**
     * REGISTRO
     * El front manda { nombre, correo, password }
     */
    public MensajeResponse registrar(RegisterRequest req) {

        // Verificar si el correo ya esta registrado
        if (usuarioRepo.existsByCorreo(req.getCorreo())) {
            return new MensajeResponse("Ya existe una cuenta con ese correo.");
        }

        // Crear y guardar el usuario
        Usuario nuevo = new Usuario();
        nuevo.setNombre(req.getNombre());
        nuevo.setCorreo(req.getCorreo());
        nuevo.setPassword(req.getPassword());

        usuarioRepo.save(nuevo);

        return new MensajeResponse("Cuenta creada exitosamente.");
    }

    public MensajeResponse recuperar(RecoverRequest req) {

        Optional<Usuario> encontrado = usuarioRepo.findByCorreo(req.getCorreo());

        if (encontrado.isEmpty()) {
            return new MensajeResponse("No se encontró una cuenta con ese correo.");
        }

        // Generar token unico de recuperacion y guardarlo
        String tokenRecuperacion = UUID.randomUUID().toString();
        Usuario usuario = encontrado.get();
        usuario.setTokenRecuperacion(tokenRecuperacion);
        usuarioRepo.save(usuario);

        // En produccion aqui se enviaria el email con el link de recuperacion
        // Por ahora solo simulamos que el correo fue enviado
        System.out.println("=== TOKEN RECUPERACION para " + req.getCorreo() + " : " + tokenRecuperacion + " ===");

        return new MensajeResponse("Correo de recuperación enviado.");
    }
}
