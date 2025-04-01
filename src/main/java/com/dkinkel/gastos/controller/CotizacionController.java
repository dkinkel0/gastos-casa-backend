package com.dkinkel.gastos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dkinkel.gastos.model.CotizacionDolar;
import com.dkinkel.gastos.service.CotizacionService;

@RestController
@RequestMapping("/api/cotizaciones")
@CrossOrigin(origins = "http://localhost:3535")
public class CotizacionController {
    @Autowired 
    private CotizacionService cotizacionService;
    
    @GetMapping
    public List<CotizacionDolar> getAllCotizaciones() {
        return cotizacionService.findAll();
    }
    
    @PostMapping
    public ResponseEntity<CotizacionDolar> createCotizacion(@RequestBody CotizacionDolar cotizacion) {
        CotizacionDolar nuevaCotizacion = cotizacionService.save(cotizacion);
        return ResponseEntity.ok(nuevaCotizacion);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CotizacionDolar> getCotizacionById(@PathVariable Long id) {
        return cotizacionService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCotizacion(@PathVariable Long id) {
        cotizacionService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
