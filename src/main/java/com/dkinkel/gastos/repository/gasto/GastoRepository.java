package com.dkinkel.gastos.repository.gasto;

import com.dkinkel.gastos.model.gasto.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
    // Buscar gastos por rango de fechas
    List<Gasto> findByFechaBetweenOrderByFechaDesc(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar los últimos X gastos ordenados por fecha descendente usando Pageable
    List<Gasto> findAllByOrderByFechaDesc(Pageable pageable);
}
