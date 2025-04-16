package com.dkinkel.gastos.model.forma;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "forma_pago")
@Data
public class FormaPago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipo;  // "EFECTIVO" o "TARJETA"

    private String banco;

    private String marcaTarjeta;  // "VISA" o "MASTERCARD"

    private Integer diaCierre;

    private Integer diaVencimiento;

    @Column(nullable = false)
    private boolean activo = true;
}