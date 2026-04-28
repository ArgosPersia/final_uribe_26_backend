package com.example.API.analitica.controladores;

import com.example.API.analitica.modelos.Venta;
import com.example.API.analitica.repositorios.VentaRepositorio;
import com.example.API.analitica.servicios.VentaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaControlador {

    @Autowired
    private VentaRepositorio ventaRepo;

    @Autowired
    private VentaServicio ventaServicio;

    @GetMapping
    public List<Venta> obtenerVentas() {
        return ventaRepo.findAll();
    }

    @PostMapping
    public Venta guardarVenta(@RequestBody Venta nuevaVenta) {
        //  devuelve los datos bien
        String estado = ventaServicio.validarConPython(nuevaVenta);
        nuevaVenta.setEstado(estado);
        return ventaRepo.save(nuevaVenta);
    }

    // Método para limpiar el historial de la tabla H2
    @DeleteMapping("/borrar-todo")
    public ResponseEntity<Void> borrarHistorial() {
        ventaRepo.deleteAll();
        return ResponseEntity.ok().build();
    }
}