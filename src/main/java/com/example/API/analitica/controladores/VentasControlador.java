package com.example.API.analitica.controladores;

import com.example.API.analitica.modelos.DTOs.VentaRequest;
import com.example.API.analitica.modelos.DTOs.VentaResponse;
import com.example.API.analitica.servicios.VentasServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentasControlador {

    private final VentasServicio ventasServicio;

    @PostMapping
    public ResponseEntity<VentaResponse> registrarVenta(@RequestBody VentaRequest req) {
        VentaResponse respuesta = ventasServicio.registrarVenta(req);
        return ResponseEntity.status(201).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<List<VentaResponse>> listarVentas() {
        return ResponseEntity.ok(ventasServicio.listarVentas());
    }

    @GetMapping("/limpias")
    public ResponseEntity<List<VentaResponse>> listarVentasLimpias() {
        return ResponseEntity.ok(ventasServicio.listarVentasLimpias());
    }

    @GetMapping("/vendedor/{nombre}")
    public ResponseEntity<List<VentaResponse>> listarPorVendedor(@PathVariable String nombre) {
        return ResponseEntity.ok(ventasServicio.listarPorVendedor(nombre));
    }
}