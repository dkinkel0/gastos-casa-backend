package com.dkinkel.gastos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tipos_gasto")
public class TipoGasto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    // Constructor, getters y setters
} 