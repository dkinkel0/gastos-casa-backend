package com.dkinkel.gastos.controller.cotizacion;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dkinkel.gastos.model.cotizacion.CotizacionDolar;
import com.dkinkel.gastos.service.cotizacion.CotizacionService;

@RestController
@RequestMapping("/api/cotizacion")
@CrossOrigin(origins = {"http://localhost:3535", "http://localhost:8585"})
public class CotizacionController {
    @Autowired 
    private CotizacionService cotizacionService;
    
    @GetMapping
    public List<CotizacionDolar> getAllCotizaciones() {
        return cotizacionService.findAll();
    }
    
    @GetMapping("/{fecha}")
    public ResponseEntity<CotizacionDolar> getCotizacionByFecha(@PathVariable String fecha) {
        LocalDate fechaLocalDate = LocalDate.parse(fecha);
        return cotizacionService.findByFecha(fechaLocalDate)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<CotizacionDolar> createCotizacion(@RequestBody CotizacionDolar cotizacion) {
        CotizacionDolar nuevaCotizacion = cotizacionService.save(cotizacion);
        return ResponseEntity.ok(nuevaCotizacion);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCotizacion(@PathVariable Long id) {
        cotizacionService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
