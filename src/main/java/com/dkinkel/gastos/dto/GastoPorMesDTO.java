package com.dkinkel.gastos.dto;

import java.math.BigDecimal;

public class GastoPorMesDTO {
    private String mesAño;
    private Long tipoGastoId;
    private String tipoGastoNombre;
    private BigDecimal totalPesos;
    private BigDecimal totalDolares;

    // Constructor vacío necesario para deserialización
    public GastoPorMesDTO() {
    }

    // Constructor con todos los campos
    public GastoPorMesDTO(String mesAño, Long tipoGastoId, String tipoGastoNombre, 
                         BigDecimal totalPesos, BigDecimal totalDolares) {
        this.mesAño = mesAño;
        this.tipoGastoId = tipoGastoId;
        this.tipoGastoNombre = tipoGastoNombre;
        this.totalPesos = totalPesos;
        this.totalDolares = totalDolares;
    }

    // Getters y setters
    public String getMesAño() {
        return mesAño;
    }

    public void setMesAño(String mesAño) {
        this.mesAño = mesAño;
    }

    public Long getTipoGastoId() {
        return tipoGastoId;
    }

    public void setTipoGastoId(Long tipoGastoId) {
        this.tipoGastoId = tipoGastoId;
    }

    public String getTipoGastoNombre() {
        return tipoGastoNombre;
    }

    public void setTipoGastoNombre(String tipoGastoNombre) {
        this.tipoGastoNombre = tipoGastoNombre;
    }

    public BigDecimal getTotalPesos() {
        return totalPesos;
    }

    public void setTotalPesos(BigDecimal totalPesos) {
        this.totalPesos = totalPesos;
    }

    public BigDecimal getTotalDolares() {
        return totalDolares;
    }

    public void setTotalDolares(BigDecimal totalDolares) {
        this.totalDolares = totalDolares;
    }

    @Override
    public String toString() {
        return "GastoPorMesDTO{" +
                "mesAño='" + mesAño + '\'' +
                ", tipoGastoId=" + tipoGastoId +
                ", tipoGastoNombre='" + tipoGastoNombre + '\'' +
                ", totalPesos=" + totalPesos +
                ", totalDolares=" + totalDolares +
                '}';
    }
} 