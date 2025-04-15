package com.dkinkel.gastos.controller.forma;

import com.dkinkel.gastos.model.forma.FormaPago;
import com.dkinkel.gastos.service.forma.FormaPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forma-pago")
@CrossOrigin(origins = "http://localhost:4200")
public class FormaPagoController {

    @Autowired
    private FormaPagoService formaPagoService;

    @PostMapping
    public ResponseEntity<FormaPago> crear(@RequestBody FormaPago formaPago) {
        return ResponseEntity.ok(formaPagoService.saveFormaPago(formaPago));
    }

    @GetMapping
    public ResponseEntity<List<FormaPago>> listarTodos() {
        return ResponseEntity.ok(formaPagoService.getAllFormasPago());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPago> obtenerPorId(@PathVariable Long id) {
        return formaPagoService.getFormaPagoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormaPago> actualizar(@PathVariable Long id, @RequestBody FormaPago formaPago) {
        FormaPago formaPagoActualizada = formaPagoService.updateFormaPago(id, formaPago);
        if (formaPagoActualizada != null) {
            return ResponseEntity.ok(formaPagoActualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return formaPagoService.getFormaPagoById(id)
                .map(formaPago -> {
                    formaPagoService.deleteFormaPago(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}