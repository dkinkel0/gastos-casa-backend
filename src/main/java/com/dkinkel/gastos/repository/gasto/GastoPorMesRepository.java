package com.dkinkel.gastos.repository.gasto;

import com.dkinkel.gastos.model.gasto.GastoPorMes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GastoPorMesRepository extends JpaRepository<GastoPorMes, Long> {
    
    // Buscar todos los gastos por mes
    List<GastoPorMes> findByMesAñoOrderByTipoGastoNombre(String mesAño);
    
    // Buscar gastos por rango de meses
    List<GastoPorMes> findByMesAñoInOrderByMesAñoDescTipoGastoNombre(List<String> meses);
    
    // Buscar gastos por tipo de gasto
    List<GastoPorMes> findByTipoGastoIdOrderByMesAñoDesc(Long tipoGastoId);
    
    // Verificar si existe un registro para un mes y tipo específico
    boolean existsByMesAñoAndTipoGastoId(String mesAño, Long tipoGastoId);
    
    // Verificar si existen registros para un mes
    boolean existsByMesAño(String mesAño);
    
    // Buscar un registro específico por mes y tipo
    GastoPorMes findByMesAñoAndTipoGastoId(String mesAño, Long tipoGastoId);
    
    // Eliminar registros por mes
    void deleteByMesAño(String mesAño);
} 