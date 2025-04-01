package com.dkinkel.gastos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dkinkel.gastos.model.CotizacionDolar;

@Repository
public interface CotizacionRepository extends JpaRepository<CotizacionDolar, Long> {
    List<CotizacionDolar> findByOrderByFechaDesc();
}
