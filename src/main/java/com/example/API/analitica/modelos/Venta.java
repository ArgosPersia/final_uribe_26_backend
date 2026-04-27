package com.example.API.analitica.modelos;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campos identicos a los del analitico Python
    @Column(nullable = false)
    private String producto;

    @Column(name = "precio_unitario", nullable = false)
    private Integer precioUnitario;

    @Column(nullable = false)
    private String talla;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private String vendedor;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private LocalDate fecha;

    // Estado despues de pasar por el analitico
    // SUCIA = recien ingresada | LIMPIA = paso validacion | INVALIDA = no paso
    @Column
    private String estado;
}
