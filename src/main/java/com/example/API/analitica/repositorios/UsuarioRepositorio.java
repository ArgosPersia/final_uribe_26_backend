package com.example.API.analitica.repositorios;

import com.example.API.analitica.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByNombre(String nombre);

    // Buscar por correo o nombre (para el login con identifier)
    Optional<Usuario> findByCorreoOrNombre(String correo, String nombre);

    boolean existsByCorreo(String correo);
}