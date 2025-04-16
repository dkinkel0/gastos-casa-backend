package com.dkinkel.gastos.model.tipo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tipo_gasto")
@Data
public class TipoGasto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(length = 255)
    private String descripcion;

    // Constructor, getters y setters
} 