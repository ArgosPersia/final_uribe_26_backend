package com.example.API.analitica.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String producto;
    private String talla;
    private Integer cantidad;
    private String vendedor;
    private Double precioUnitario;
    private Double total;
    private String fecha;
    private String estado; // Aquí guardamos lo que diga Python: LIMPIA o INVALIDA

    // Getters y Setters (Necesarios para que Spring lea los datos)
    public Long getId() { return id; }
    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }
    public String getTalla() { return talla; }
    public void setTalla(String talla) { this.talla = talla; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public String getVendedor() { return vendedor; }
    public void setVendedor(String vendedor) { this.vendedor = vendedor; }
    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}