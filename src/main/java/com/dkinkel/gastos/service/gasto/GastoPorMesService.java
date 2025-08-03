package com.dkinkel.gastos.service.gasto;

import com.dkinkel.gastos.model.gasto.Gasto;
import com.dkinkel.gastos.model.gasto.GastoPorMes;
import com.dkinkel.gastos.repository.gasto.GastoRepository;
import com.dkinkel.gastos.repository.gasto.GastoPorMesRepository;
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

    @Autowired
    public GastoPorMesService(GastoRepository gastoRepository, GastoPorMesRepository gastoPorMesRepository) {
        this.gastoRepository = gastoRepository;
        this.gastoPorMesRepository = gastoPorMesRepository;
    }

    /**
     * Calcula y guarda los gastos por mes para un mes específico
     * @param mesAño formato "2024-01"
     */
    public void calcularYGuardarGastosPorMes(String mesAño) {
        // Verificar si existen registros antes de eliminar
        if (gastoPorMesRepository.existsByMesAño(mesAño)) {
            gastoPorMesRepository.deleteByMesAño(mesAño);
        }
        
        // Obtener todos los gastos del mes
        LocalDate fechaInicio = LocalDate.parse(mesAño + "-01");
        LocalDate fechaFin = fechaInicio.plusMonths(1).minusDays(1);
        
        List<Gasto> gastosDelMes = gastoRepository.findByFechaBetweenOrderByFechaDesc(fechaInicio, fechaFin);
        
        // Si no hay gastos en el mes, no crear registros
        if (gastosDelMes.isEmpty()) {
            return; // Mes sin gastos, no crear registros
        }
        
        // Agrupar por tipo de gasto y sumar
        Map<Long, List<Gasto>> gastosPorTipo = gastosDelMes.stream()
                .collect(Collectors.groupingBy(gasto -> gasto.getTipo().getId()));
        
        // Crear registros GastoPorMes
        gastosPorTipo.forEach((tipoId, gastos) -> {
            BigDecimal totalPesos = gastos.stream()
                    .map(Gasto::getCosto)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            BigDecimal totalDolares = gastos.stream()
                    .map(Gasto::getCostoDolar)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            GastoPorMes gastoPorMes = new GastoPorMes(
                mesAño,
                gastos.get(0).getTipo(),
                totalPesos,
                totalDolares
            );
            
            gastoPorMesRepository.save(gastoPorMes);
        });
    }

    /**
     * Calcula y guarda los gastos por mes para el mes actual
     */
    public void calcularMesActual() {
        String mesActual = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        calcularYGuardarGastosPorMes(mesActual);
    }

    /**
     * Calcula y guarda los gastos por mes para todos los meses desde septiembre 2023
     */
    public void calcularMesesPrevios() {
        LocalDate fechaInicio = LocalDate.of(2023, 9, 1);
        LocalDate fechaActual = LocalDate.now();
        
        while (!fechaInicio.isAfter(fechaActual)) {
            String mesAño = fechaInicio.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            calcularYGuardarGastosPorMes(mesAño);
            fechaInicio = fechaInicio.plusMonths(1);
        }
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