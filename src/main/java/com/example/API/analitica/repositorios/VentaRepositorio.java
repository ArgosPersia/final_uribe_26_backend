package com.example.API.analitica.repositorios;

import com.example.API.analitica.modelos.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepositorio extends JpaRepository<Venta, Long> {

    // Todas las ventas de un vendedor especifico
    List<Venta> findByVendedor(String vendedor);

    // Ventas por estado (LIMPIA, SUCIA, INVALIDA)
    List<Venta> findByEstado(String estado);

    // Ventas de un producto especifico
    List<Venta> findByProducto(String producto);
}

