package com.example.API.analitica.servicios;

import com.example.API.analitica.modelos.DTOs.VentaRequest;
import com.example.API.analitica.modelos.DTOs.VentaResponse;
import com.example.API.analitica.modelos.Venta;
import com.example.API.analitica.repositorios.VentaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentasServicio {

    private final VentaRepositorio ventaRepo;
    private final ValidadorVenta validador;

    /**
     * REGISTRAR VENTA
     * Recibe los datos del front, aplica la logica del analitico
     * y guarda la venta con su estado (LIMPIA o INVALIDA)
     */
    public VentaResponse registrarVenta(VentaRequest req) {

        // Construir entidad Venta desde el request
        Venta venta = new Venta();
        venta.setProducto(req.getProducto());
        venta.setPrecioUnitario(req.getPrecioUnitario());
        venta.setTalla(req.getTalla());
        venta.setCantidad(req.getCantidad());
        venta.setVendedor(req.getVendedor());
        venta.setTotal(req.getTotal());

        // Parsear la fecha - acepta "2026-01-15" o "15/01/2026"
        venta.setFecha(parsearFecha(req.getFecha()));

        // ─── Aplicar logica del analitico (ValidadorVenta) ──────────
        String estado = validador.limpiarYValidar(venta);
        venta.setEstado(estado);

        // Guardar en H2
        Venta guardada = ventaRepo.save(venta);

        // Armar respuesta
        String mensaje = estado.equals("LIMPIA")
                ? "Venta registrada correctamente."
                : "Venta registrada pero contiene datos invalidos. Revise los campos.";

        return construirVentaResponse(guardada, mensaje);
    }

    /**
     * LISTAR TODAS LAS VENTAS
     */
    public List<VentaResponse> listarVentas() {
        return ventaRepo.findAll()
                .stream()
                .map(v -> construirVentaResponse(v, null))
                .collect(Collectors.toList());
    }

    /**
     * LISTAR VENTAS LIMPIAS
     */
    public List<VentaResponse> listarVentasLimpias() {
        return ventaRepo.findByEstado("LIMPIA")
                .stream()
                .map(v -> construirVentaResponse(v, null))
                .collect(Collectors.toList());
    }

    /**
     * LISTAR VENTAS POR VENDEDOR
     */
    public List<VentaResponse> listarPorVendedor(String vendedor) {
        return ventaRepo.findByVendedor(vendedor)
                .stream()
                .map(v -> construirVentaResponse(v, null))
                .collect(Collectors.toList());
    }

    //  Helpers

    private LocalDate parsearFecha(String fechaStr) {
        if (fechaStr == null) return LocalDate.now();
        try {
            // Formato ISO: 2026-01-15
            return LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e1) {
            try {
                // Formato dia/mes/anio: 15/01/2026
                return LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e2) {
                return LocalDate.now();
            }
        }
    }

    private VentaResponse construirVentaResponse(Venta v, String mensaje) {
        return new VentaResponse(
                v.getId(),
                v.getProducto(),
                v.getPrecioUnitario(),
                v.getTalla(),
                v.getCantidad(),
                v.getVendedor(),
                v.getTotal(),
                v.getFecha() != null ? v.getFecha().toString() : null,
                v.getEstado(),
                mensaje
        );
    }
}
