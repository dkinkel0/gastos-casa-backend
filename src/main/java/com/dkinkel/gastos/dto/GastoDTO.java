package com.dkinkel.gastos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GastoDTO {
    private LocalDate fecha;
    private String detalle;
    private Long tipoId;
    private BigDecimal costo;
    private BigDecimal costoDolar;
    private Long formaPagoId;
    private Integer cuotas;

    // Constructor vacío necesario para deserialización
    public GastoDTO() {
    }

    // Constructor con todos los campos
    public GastoDTO(LocalDate fecha, String detalle, Long tipoId, BigDecimal costo, 
                   BigDecimal costoDolar, Long formaPagoId, Integer cuotas) {
        this.fecha = fecha;
        this.detalle = detalle;
        this.tipoId = tipoId;
        this.costo = costo;
        this.costoDolar = costoDolar;
        this.formaPagoId = formaPagoId;
        this.cuotas = cuotas;
    }

    // Getters y setters
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

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
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

    public Long getFormaPagoId() {
        return formaPagoId;
    }

    public void setFormaPagoId(Long formaPagoId) {
        this.formaPagoId = formaPagoId;
    }

    public Integer getCuotas() {
        return cuotas;
    }

    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    @Override
    public String toString() {
        return "GastoDTO{" +
                "fecha=" + fecha +
                ", detalle='" + detalle + '\'' +
                ", tipoId=" + tipoId +
                ", costo=" + costo +
                ", costoDolar=" + costoDolar +
                ", formaPagoId=" + formaPagoId +
                ", cuotas=" + cuotas +
                '}';
    }
}
