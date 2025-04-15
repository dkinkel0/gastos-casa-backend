package com.dkinkel.gastos.controller.tipo;

import com.dkinkel.gastos.model.tipo.TipoGasto;
import com.dkinkel.gastos.service.tipo.TipoGastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-gasto")
@CrossOrigin(origins = "http://localhost:3535")
public class TipoGastoController {

    @Autowired
    private TipoGastoService tipoGastoService;

    @PostMapping
    public ResponseEntity<TipoGasto> crear(@RequestBody TipoGasto tipoGasto) {
        return ResponseEntity.ok(tipoGastoService.saveTipoGasto(tipoGasto));
    }

    @GetMapping
    public ResponseEntity<List<TipoGasto>> listarTodos() {
        return ResponseEntity.ok(tipoGastoService.getAllTiposGasto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoGasto> obtenerPorId(@PathVariable Long id) {
        return tipoGastoService.getTipoGastoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoGasto> actualizar(@PathVariable Long id, @RequestBody TipoGasto tipoGasto) {
        TipoGasto tipoGastoActualizado = tipoGastoService.updateTipoGasto(id, tipoGasto);
        if (tipoGastoActualizado != null) {
            return ResponseEntity.ok(tipoGastoActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return tipoGastoService.getTipoGastoById(id)
                .map(tipoGasto -> {
                    tipoGastoService.deleteTipoGasto(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
