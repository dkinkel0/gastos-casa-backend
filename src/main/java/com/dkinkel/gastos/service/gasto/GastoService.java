package com.dkinkel.gastos.service.gasto;

import com.dkinkel.gastos.model.gasto.Gasto;
import com.dkinkel.gastos.repository.gasto.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GastoService {

    private final GastoRepository gastoRepository;

    @Autowired
    public GastoService(GastoRepository gastoRepository) {
        this.gastoRepository = gastoRepository;
    }

    public Gasto guardarGasto(Gasto gasto) {
        return gastoRepository.save(gasto);
    }

    public List<Gasto> obtenerTodosLosGastos() {
        return gastoRepository.findAll();
    }

    public Optional<Gasto> obtenerGastoPorId(Long id) {
        return gastoRepository.findById(id);
    }

    public void eliminarGasto(Long id) {
        gastoRepository.deleteById(id);
    }

    public Gasto actualizarGasto(Long id, Gasto gastoActualizado) {
        return gastoRepository.findById(id)
            .map(gastoExistente -> {
                gastoExistente.setFecha(gastoActualizado.getFecha());
                gastoExistente.setDetalle(gastoActualizado.getDetalle());
                gastoExistente.setTipo(gastoActualizado.getTipo());
                gastoExistente.setCosto(gastoActualizado.getCosto());
                gastoExistente.setCostoDolar(gastoActualizado.getCostoDolar());
                gastoExistente.setFormaPago(gastoActualizado.getFormaPago());
                gastoExistente.setCuotas(gastoActualizado.getCuotas());
                return gastoRepository.save(gastoExistente);
            })
            .orElseGet(() -> {
                gastoActualizado.setId(id);
                return gastoRepository.save(gastoActualizado);
            });
    }
}
