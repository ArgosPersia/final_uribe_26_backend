package com.example.API.analitica.repositorios;

import com.example.API.analitica.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    // Buscar por correo (para login con correo)
    Optional<Usuario> findByCorreo(String correo);

    // Buscar por nombre (para login con usuario)
    Optional<Usuario> findByNombre(String nombre);

    // Verificar si ya existe el correo antes de registrar
    boolean existsByCorreo(String correo);

    // Buscar por token de recuperacion
    Optional<Usuario> findByTokenRecuperacion(String token);
}
