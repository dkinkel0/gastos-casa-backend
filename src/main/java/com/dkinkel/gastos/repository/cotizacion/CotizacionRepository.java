package com.dkinkel.gastos.repository.cotizacion;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dkinkel.gastos.model.cotizacion.CotizacionDolar;

@Repository
public interface CotizacionRepository extends JpaRepository<CotizacionDolar, Long> {
    List<CotizacionDolar> findByOrderByFechaDesc();
    Optional<CotizacionDolar> findByFecha(LocalDate fecha);
}
