package com.dkinkel.gastos.model.cotizacion;

import java.time.LocalDate;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "cotizacion_dolar")
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

    @Column(nullable = false)
    private BigDecimal precioIntermedio;

    // Constructor vacío
    public CotizacionDolar() {}

    // Constructor con parámetros
    public CotizacionDolar(LocalDate fecha, BigDecimal precioCompra, BigDecimal precioVenta) {
        this.fecha = fecha;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.precioIntermedio = calcularPrecioIntermedio();
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

    public BigDecimal getPrecioIntermedio() {
        return precioIntermedio;
    }

    public void setPrecioIntermedio(BigDecimal precioIntermedio) {
        this.precioIntermedio = precioIntermedio;
    }

    // Método para calcular el precio promedio
    public BigDecimal getPrecioPromedio() {
        return precioCompra.add(precioVenta).divide(BigDecimal.valueOf(2));
    }

    // Método para calcular el precio intermedio
    private BigDecimal calcularPrecioIntermedio() {
        return precioCompra.add(precioVenta).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
    }

    // Método para actualizar el precio intermedio cuando cambien los precios
    @PrePersist
    @PreUpdate
    private void actualizarPrecioIntermedio() {
        this.precioIntermedio = calcularPrecioIntermedio();
    }
}