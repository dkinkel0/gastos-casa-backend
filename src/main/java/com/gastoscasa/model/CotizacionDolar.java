package com.gastoscasa.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "cotizaciones_dolar")
public class CotizacionDolar {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private BigDecimal precioCompra;

    @Column(nullable = false)
    private BigDecimal precioVenta;

    // Constructor vacío
    public CotizacionDolar() {}

    // Constructor con parámetros
    public CotizacionDolar(LocalDate fecha, BigDecimal precioCompra, BigDecimal precioVenta) {
        this.fecha = fecha;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    // Método para calcular el precio promedio
    public BigDecimal getPrecioPromedio() {
        return precioCompra.add(precioVenta).divide(BigDecimal.valueOf(2));
    }
}