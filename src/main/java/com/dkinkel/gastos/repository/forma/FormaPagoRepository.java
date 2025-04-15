package com.dkinkel.gastos.repository.forma;

import com.dkinkel.gastos.model.forma.FormaPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FormaPagoRepository extends JpaRepository<FormaPago, Long> {
    
    // Buscar formas de pago activas
    List<FormaPago> findByActivoTrue();
    
    // Buscar por tipo (para listar solo efectivo o solo tarjetas)
    List<FormaPago> findByTipoAndActivoTrue(String tipo);
}