package com.dkinkel.gastos.model.gasto;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.dkinkel.gastos.model.tipo.TipoGasto;
import com.dkinkel.gastos.model.forma.FormaPago;

@Entity
@Table(name = "gasto")
public class Gasto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha; // Fecha ingresada manualmente por el usuario

    @Column(nullable = false)
    private String detalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_gasto_id", nullable = false)
    private TipoGasto tipo;

    @Column(nullable = false)
    private BigDecimal costo;

    @Column(name = "costo_dolar")
    private BigDecimal costoDolar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pago_id", nullable = false)
    private FormaPago formaPago;

    @Column
    private Integer cuotas;

    // Constructor vacío
    public Gasto() {}

    // Constructor con parámetros
    public Gasto(LocalDate fecha, String detalle, TipoGasto tipo, BigDecimal costo, 
                 BigDecimal costoDolar, FormaPago formaPago, Integer cuotas) {
        this.fecha = fecha;
        this.detalle = detalle;
        this.tipo = tipo;
        this.costo = costo;
        this.costoDolar = costoDolar;
        this.formaPago = formaPago;
        this.cuotas = cuotas;
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public TipoGasto getTipo() {
        return tipo;
    }

    public void setTipo(TipoGasto tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getCostoDolar() {
        return costoDolar;
    }

    public void setCostoDolar(BigDecimal costoDolar) {
        this.costoDolar = costoDolar;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Integer getCuotas() {
        return cuotas;
    }

    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }
} 