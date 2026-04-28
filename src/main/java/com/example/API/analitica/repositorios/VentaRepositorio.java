package com.example.API.analitica.repositorios;

import com.example.API.analitica.modelos.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepositorio extends JpaRepository<Venta, Long> {
    // No necesitas escribir nada aquí, JpaRepository ya hace todo el trabajo
}