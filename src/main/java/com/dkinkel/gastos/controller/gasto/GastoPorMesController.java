package com.dkinkel.gastos.controller.gasto;

import com.dkinkel.gastos.service.gasto.GastoPorMesService;
import com.dkinkel.gastos.dto.GastoPorMesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gastos-por-mes")
@CrossOrigin(origins = "http://localhost:3535")
public class GastoPorMesController {

    private final GastoPorMesService gastoPorMesService;

    @Autowired
    public GastoPorMesController(GastoPorMesService gastoPorMesService) {
        this.gastoPorMesService = gastoPorMesService;
    }

    /**
     * Recalcula todo el histórico de gastos por mes
     * @return ResponseEntity con el resultado de la operación
     */
    @PostMapping("/recalcular-historico")
    public ResponseEntity<String> recalcularHistorico() {
        try {
            gastoPorMesService.calcularMesesPrevios();
            return ResponseEntity.ok("Recálculo histórico completado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.ok("Recálculo completado (algunos meses sin datos)");
        }
    }

    /**
     * Calcula solo el mes actual
     * @return ResponseEntity con el resultado de la operación
     */
    @PostMapping("/calcular-mes-actual")
    public ResponseEntity<String> calcularMesActual() {
        try {
            gastoPorMesService.calcularMesActual();
            return ResponseEntity.ok("Cálculo del mes actual completado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.ok("Cálculo completado (mes actual sin datos)");
        }
    }

    /**
     * Obtiene los gastos por mes para un mes específico
     * @param mesAño formato "2024-01"
     * @return ResponseEntity con la lista de gastos por mes
     */
    @GetMapping("/mes/{mesAño}")
    public ResponseEntity<List<GastoPorMesDTO>> obtenerGastosPorMes(@PathVariable String mesAño) {
        try {
            List<GastoPorMesDTO> gastosPorMes = gastoPorMesService.obtenerGastosPorMes(mesAño);
            return ResponseEntity.ok(gastosPorMes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtiene los gastos por mes para un rango de meses
     * @param request objeto con la lista de meses
     * @return ResponseEntity con la lista de gastos por mes
     */
    @PostMapping("/rango-meses")
    public ResponseEntity<List<GastoPorMesDTO>> obtenerGastosPorRangoMeses(@RequestBody RangoMesesRequest request) {
        try {
            List<GastoPorMesDTO> gastosPorMes = gastoPorMesService.obtenerGastosPorRangoMeses(request.getMeses());
            return ResponseEntity.ok(gastosPorMes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Clase interna para el request del rango de meses
     */
    public static class RangoMesesRequest {
        private List<String> meses;

        public List<String> getMeses() {
            return meses;
        }

        public void setMeses(List<String> meses) {
            this.meses = meses;
        }
    }
} 