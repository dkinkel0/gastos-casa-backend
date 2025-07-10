package com.dkinkel.gastos.controller.gasto;

import com.dkinkel.gastos.dto.GastoDTO;
import com.dkinkel.gastos.model.forma.FormaPago;
import com.dkinkel.gastos.model.gasto.Gasto;
import com.dkinkel.gastos.model.tipo.TipoGasto;
import com.dkinkel.gastos.repository.forma.FormaPagoRepository;
import com.dkinkel.gastos.repository.tipo.TipoGastoRepository;
import com.dkinkel.gastos.service.gasto.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/gasto")
@CrossOrigin(origins = "http://localhost:3535") 
public class GastoController {

    private final GastoService gastoService;
    private final TipoGastoRepository tipoGastoRepository;
    private final FormaPagoRepository formaPagoRepository;

    @Autowired 
    public GastoController(GastoService gastoService, 
                          TipoGastoRepository tipoGastoRepository,
                          FormaPagoRepository formaPagoRepository) {
        this.gastoService = gastoService;
        this.tipoGastoRepository = tipoGastoRepository;
        this.formaPagoRepository = formaPagoRepository;
    }

    @PostMapping
    public ResponseEntity<?> crearGasto(@RequestBody GastoDTO dto) {
        try {
            // Validar que los IDs no sean nulos
            if (dto.getTipoId() == null) {
                Map<String, String> error = new HashMap<>();
                error.put("mensaje", "El tipo de gasto no puede ser nulo");
                return ResponseEntity.badRequest().body(error);
            }
            
            if (dto.getFormaPagoId() == null) {
                Map<String, String> error = new HashMap<>();
                error.put("mensaje", "La forma de pago no puede ser nula");
                return ResponseEntity.badRequest().body(error);
            }
            
            // Convertir DTO a entidad
            Gasto gasto = new Gasto();
            gasto.setFecha(dto.getFecha());
            gasto.setDetalle(dto.getDetalle());
            gasto.setCosto(dto.getCosto());
            gasto.setCostoDolar(dto.getCostoDolar());
            gasto.setCuotas(dto.getCuotas());
            
            // Obtener y asignar relaciones
            TipoGasto tipo = tipoGastoRepository.findById(dto.getTipoId())
                .orElseThrow(() -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("mensaje", "TipoGasto no encontrado con ID: " + dto.getTipoId());
                    return new RuntimeException(error.get("mensaje"));
                });
            gasto.setTipo(tipo);
            
            FormaPago formaPago = formaPagoRepository.findById(dto.getFormaPagoId())
                .orElseThrow(() -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("mensaje", "FormaPago no encontrado con ID: " + dto.getFormaPagoId());
                    return new RuntimeException(error.get("mensaje"));
                });
            gasto.setFormaPago(formaPago);
            
            // Guardar y devolver
            Gasto nuevoGasto = gastoService.guardarGasto(gasto);
            return ResponseEntity.ok(nuevoGasto);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Error al crear gasto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<List<Gasto>> obtenerTodosLosGastos() {
        List<Gasto> gastos = gastoService.obtenerTodosLosGastos();
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gasto> obtenerGastoPorId(@PathVariable Long id) {
        return gastoService.obtenerGastoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarGasto(@PathVariable Long id, @RequestBody GastoDTO dto) {
        try {
            // Validar que los IDs no sean nulos
            if (dto.getTipoId() == null) {
                Map<String, String> error = new HashMap<>();
                error.put("mensaje", "El tipo de gasto no puede ser nulo");
                return ResponseEntity.badRequest().body(error);
            }
            
            if (dto.getFormaPagoId() == null) {
                Map<String, String> error = new HashMap<>();
                error.put("mensaje", "La forma de pago no puede ser nula");
                return ResponseEntity.badRequest().body(error);
            }
            
            // Obtener el gasto existente
            Gasto gastoExistente = gastoService.obtenerGastoPorId(id)
                .orElseThrow(() -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("mensaje", "Gasto no encontrado con ID: " + id);
                    return new RuntimeException(error.get("mensaje"));
                });
            
            // Actualizar los campos del gasto existente
            gastoExistente.setFecha(dto.getFecha());
            gastoExistente.setDetalle(dto.getDetalle());
            gastoExistente.setCosto(dto.getCosto());
            gastoExistente.setCostoDolar(dto.getCostoDolar());
            gastoExistente.setCuotas(dto.getCuotas());
            
            // Obtener y asignar relaciones
            TipoGasto tipo = tipoGastoRepository.findById(dto.getTipoId())
                .orElseThrow(() -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("mensaje", "TipoGasto no encontrado con ID: " + dto.getTipoId());
                    return new RuntimeException(error.get("mensaje"));
                });
            gastoExistente.setTipo(tipo);
            
            FormaPago formaPago = formaPagoRepository.findById(dto.getFormaPagoId())
                .orElseThrow(() -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("mensaje", "FormaPago no encontrado con ID: " + dto.getFormaPagoId());
                    return new RuntimeException(error.get("mensaje"));
                });
            gastoExistente.setFormaPago(formaPago);
            
            // Guardar y devolver
            Gasto gastoActualizado = gastoService.guardarGasto(gastoExistente);
            return ResponseEntity.ok(gastoActualizado);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Error al actualizar gasto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGasto(@PathVariable Long id) {
        gastoService.eliminarGasto(id);
        return ResponseEntity.ok().build();
    }
    
    /**
     * Obtiene gastos por rango de fechas
     * @param fechaInicio fecha de inicio en formato YYYY-MM-DD
     * @param fechaFin fecha de fin en formato YYYY-MM-DD
     * @return lista de gastos ordenados por fecha descendente
     */
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Gasto>> obtenerGastosPorRangoFechas(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        try {
            List<Gasto> gastos = gastoService.obtenerGastosPorRangoFechas(fechaInicio, fechaFin);
            return ResponseEntity.ok(gastos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Obtiene los últimos X gastos ordenados por fecha descendente
     * @param cantidad cantidad de gastos a obtener
     * @return lista de gastos ordenados por fecha descendente
     */
    @GetMapping("/ultimos")
    public ResponseEntity<List<Gasto>> obtenerUltimosGastos(@RequestParam int cantidad) {
        try {
            List<Gasto> gastos = gastoService.obtenerUltimosGastos(cantidad);
            return ResponseEntity.ok(gastos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
