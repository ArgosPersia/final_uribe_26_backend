package com.example.API.analitica.servicios;

import com.example.API.analitica.modelos.Venta;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * ValidadorVenta
 * Replica la logica de limpiarDatos.py del analitico Python.
 * Reglas identicas al Python:
 *  - Textos: strip + Title Case para producto y vendedor, UPPER para talla
 *  - Tallas validas: XS, S, M, L, XL, XXL
 *  - Cantidad debe ser > 0 y no negativa
 *  - total debe ser igual a precioUnitario * cantidad
 *  - Campos obligatorios no nulos
 *  - Vendedor: solo letras y espacios, minimo 3 caracteres
 *  - PrecioUnitario: debe coincidir con el catalogo
 */
@Component
public class ValidadorVenta {

    private static final Set<String> TALLAS_VALIDAS = Set.of("XS", "S", "M", "L", "XL", "XXL");

    // Catalogo oficial — igual que en Python y en el front
    private static final java.util.Map<String, Integer> CATALOGO = java.util.Map.of(
            "Camisa Polo Slim Fit Algodon",  200000,
            "Jeans Classic Blue Denim",       180000,
            "Chaqueta Bomber Impermeable",    350000,
            "Camiseta Basica Cuello V",        85000,
            "Bermuda Cargo Beige",            120000,
            "Saco Cuello Tortuga Lana",       220000
    );

    public String limpiarYValidar(Venta venta) {

        // ── 1. Limpiar textos ───────────────────────────────────────
        if (venta.getProducto() != null) {
            venta.setProducto(convertirTitleCase(venta.getProducto().trim()));
        }
        if (venta.getVendedor() != null) {
            venta.setVendedor(convertirTitleCase(venta.getVendedor().trim()));
        }
        if (venta.getTalla() != null) {
            venta.setTalla(venta.getTalla().trim().toUpperCase());
        }

        // ── 2. Campos obligatorios no nulos ─────────────────────────
        if (venta.getProducto()      == null || venta.getProducto().isBlank())  return "INVALIDA";
        if (venta.getPrecioUnitario() == null)                                  return "INVALIDA";
        if (venta.getCantidad()       == null)                                  return "INVALIDA";
        if (venta.getTotal()          == null)                                  return "INVALIDA";
        if (venta.getVendedor()       == null || venta.getVendedor().isBlank()) return "INVALIDA";
        if (venta.getTalla()          == null || venta.getTalla().isBlank())    return "INVALIDA";

        // ── 3. Vendedor: solo letras y espacios, minimo 3 caracteres ─
        if (!venta.getVendedor().matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]{3,}")) return "INVALIDA";

        // ── 4. Talla valida ─────────────────────────────────────────
        if (!TALLAS_VALIDAS.contains(venta.getTalla())) return "INVALIDA";

        // ── 5. Cantidad > 0 (rechaza negativos y cero) ─────────────
        if (venta.getCantidad() <= 0) return "INVALIDA";

        // ── 6. Precio unitario debe coincidir con el catalogo ───────
        Integer precioEsperado = CATALOGO.get(venta.getProducto());
        if (precioEsperado == null) return "INVALIDA";
        if (!precioEsperado.equals(venta.getPrecioUnitario())) return "INVALIDA";

        // ── 7. total == precioUnitario * cantidad ───────────────────
        double totalEsperado = (double) venta.getPrecioUnitario() * venta.getCantidad();
        if (Math.abs(venta.getTotal() - totalEsperado) > 0.01) return "INVALIDA";

        return "LIMPIA";
    }

    private String convertirTitleCase(String texto) {
        if (texto == null || texto.isBlank()) return texto;
        String[] palabras = texto.toLowerCase().split("\\s+");
        StringBuilder resultado = new StringBuilder();
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                resultado.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1))
                        .append(" ");
            }
        }
        return resultado.toString().trim();
    }
}