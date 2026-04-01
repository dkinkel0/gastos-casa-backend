package com.dkinkel.gastos.service.gasto;

import com.dkinkel.gastos.model.gasto.Gasto;
import com.dkinkel.gastos.model.gasto.GastoPorMes;
import com.dkinkel.gastos.model.tipo.TipoGasto;
import com.dkinkel.gastos.repository.gasto.GastoRepository;
import com.dkinkel.gastos.repository.gasto.GastoPorMesRepository;
import com.dkinkel.gastos.repository.tipo.TipoGastoRepository;
import com.dkinkel.gastos.dto.GastoPorMesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GastoPorMesService {

    private final GastoRepository gastoRepository;
    private final GastoPorMesRepository gastoPorMesRepository;
    private final TipoGastoRepository tipoGastoRepository;

    @Autowired
    public GastoPorMesService(GastoRepository gastoRepository, GastoPorMesRepository gastoPorMesRepository, TipoGastoRepository tipoGastoRepository) {
        this.gastoRepository = gastoRepository;
        this.gastoPorMesRepository = gastoPorMesRepository;
        this.tipoGastoRepository = tipoGastoRepository;
    }

    /**
     * Calcula y guarda los gastos por mes para un mes específico
     * @param mesAño formato "2024-01"
     */
    public void calcularYGuardarGastosPorMes(String mesAño) {
        System.out.println("=== Calculando gastos para el mes: " + mesAño + " ===");
        
        // Verificar si existen registros antes de eliminar
        if (gastoPorMesRepository.existsByMesAño(mesAño)) {
            System.out.println("Eliminando registros existentes para el mes: " + mesAño);
            gastoPorMesRepository.deleteByMesAño(mesAño);
        }
        
        // Obtener todos los gastos del mes
        LocalDate fechaInicio = LocalDate.parse(mesAño + "-01");
        LocalDate fechaFin = fechaInicio.plusMonths(1).minusDays(1);
        
        System.out.println("Buscando gastos entre: " + fechaInicio + " y " + fechaFin);
        
        List<Gasto> gastosDelMes = gastoRepository.findByFechaBetweenOrderByFechaDesc(fechaInicio, fechaFin);
        System.out.println("Gastos encontrados en el mes: " + gastosDelMes.size());
        
        // Obtener todos los tipos de gasto disponibles
        List<TipoGasto> todosLosTipos = tipoGastoRepository.findAll();
        System.out.println("Tipos de gasto disponibles: " + todosLosTipos.size());
        
        // Agrupar gastos por tipo de gasto
        Map<Long, List<Gasto>> gastosPorTipo = gastosDelMes.stream()
                .collect(Collectors.groupingBy(gasto -> gasto.getTipo().getId()));
        
        System.out.println("Gastos agrupados por tipo: " + gastosPorTipo.size() + " tipos con gastos");
        
        // Crear registros para todos los tipos de gasto, incluso si no hay gastos
        for (TipoGasto tipo : todosLosTipos) {
            List<Gasto> gastosDelTipo = gastosPorTipo.get(tipo.getId());
            
            BigDecimal totalPesos = BigDecimal.ZERO;
            BigDecimal totalDolares = BigDecimal.ZERO;
            
            if (gastosDelTipo != null && !gastosDelTipo.isEmpty()) {
                totalPesos = gastosDelTipo.stream()
                        .map(Gasto::getCosto)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                
                totalDolares = gastosDelTipo.stream()
                        .map(Gasto::getCostoDolar)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                
                System.out.println("Tipo: " + tipo.getNombre() + " - Gastos: " + gastosDelTipo.size() + 
                                 " - Total Pesos: " + totalPesos + " - Total Dólares: " + totalDolares);
            } else {
                System.out.println("Tipo: " + tipo.getNombre() + " - Sin gastos en este mes");
            }
            
            GastoPorMes gastoPorMes = new GastoPorMes(
                mesAño,
                tipo,
                totalPesos,
                totalDolares
            );
            
            gastoPorMesRepository.save(gastoPorMes);
        }
        
        System.out.println("=== Fin del cálculo para el mes: " + mesAño + " ===");
    }

    /**
     * Calcula y guarda los gastos por mes para el mes actual
     */
    public void calcularMesActual() {
        String mesActual = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        System.out.println("Calculando mes actual: " + mesActual);
        calcularYGuardarGastosPorMes(mesActual);
        System.out.println("Cálculo del mes actual completado: " + mesActual);
    }

    /**
     * Calcula y guarda los gastos por mes para todos los meses desde septiembre 2023
     */
    public void calcularMesesPrevios() {
        System.out.println("=== Iniciando recálculo histórico ===");
        LocalDate fechaInicio = LocalDate.of(2023, 9, 1);
        LocalDate fechaActual = LocalDate.now();
        
        System.out.println("Recalculando desde: " + fechaInicio + " hasta: " + fechaActual);
        
        while (!fechaInicio.isAfter(fechaActual)) {
            String mesAño = fechaInicio.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            System.out.println("Procesando mes: " + mesAño);
            calcularYGuardarGastosPorMes(mesAño);
            fechaInicio = fechaInicio.plusMonths(1);
        }
        
        System.out.println("=== Recálculo histórico completado ===");
    }

    /**
     * Obtiene todos los gastos por mes para un mes específico
     * @param mesAño formato "2024-01"
     * @return lista de DTOs con los gastos por tipo
     */
    public List<GastoPorMesDTO> obtenerGastosPorMes(String mesAño) {
        List<GastoPorMes> gastosPorMes = gastoPorMesRepository.findByMesAñoOrderByTipoGastoNombre(mesAño);
        
        return gastosPorMes.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los gastos por mes para un rango de meses
     * @param meses lista de meses en formato "2024-01"
     * @return lista de DTOs con los gastos por tipo
     */
    public List<GastoPorMesDTO> obtenerGastosPorRangoMeses(List<String> meses) {
        List<GastoPorMes> gastosPorMes = gastoPorMesRepository.findByMesAñoInOrderByMesAñoDescTipoGastoNombre(meses);
        
        return gastosPorMes.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte un GastoPorMes a DTO
     */
    private GastoPorMesDTO convertirADTO(GastoPorMes gastoPorMes) {
        return new GastoPorMesDTO(
            gastoPorMes.getMesAño(),
            gastoPorMes.getTipoGasto().getId(),
            gastoPorMes.getTipoGasto().getNombre(),
            gastoPorMes.getTotalPesos(),
            gastoPorMes.getTotalDolares()
        );
    }

   
} 