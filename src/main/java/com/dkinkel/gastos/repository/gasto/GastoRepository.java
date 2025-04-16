package com.dkinkel.gastos.repository.gasto;

import com.dkinkel.gastos.model.gasto.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
    // Aquí podemos agregar métodos personalizados de búsqueda si los necesitamos
}
