package com.gastoscasa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "formas_pago")
public class FormaPago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    // Constructor, getters y setters
} 