package com.dkinkel.gastos.model.gasto;

import jakarta.persistence.*;
import java.math.BigDecimal;
import com.dkinkel.gastos.model.tipo.TipoGasto;

@Entity
@Table(name = "gasto_por_mes")
public class GastoPorMes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mes_ano", nullable = false)
    private String mesAño; // Formato: "2024-01", "2024-02", etc.

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_gasto_id", nullable = false)
    private TipoGasto tipoGasto;

    @Column(name = "total_pesos", nullable = false)
    private BigDecimal totalPesos;

    @Column(name = "total_dolares", nullable = false)
    private BigDecimal totalDolares;

    // Constructor vacío
    public GastoPorMes() {}

    // Constructor con parámetros
    public GastoPorMes(String mesAño, TipoGasto tipoGasto, BigDecimal totalPesos, BigDecimal totalDolares) {
        this.mesAño = mesAño;
        this.tipoGasto = tipoGasto;
        this.totalPesos = totalPesos;
        this.totalDolares = totalDolares;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMesAño() {
        return mesAño;
    }

    public void setMesAño(String mesAño) {
        this.mesAño = mesAño;
    }

    public TipoGasto getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(TipoGasto tipoGasto) {
        this.tipoGasto = tipoGasto;
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
} 