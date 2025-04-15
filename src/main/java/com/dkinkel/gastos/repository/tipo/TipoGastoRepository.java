package com.dkinkel.gastos.repository.tipo;

import com.dkinkel.gastos.model.tipo.TipoGasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TipoGastoRepository extends JpaRepository<TipoGasto, Long> {
    List<TipoGasto> findByActivoTrue();
}
