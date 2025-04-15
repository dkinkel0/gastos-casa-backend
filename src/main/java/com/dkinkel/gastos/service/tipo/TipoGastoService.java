package com.dkinkel.gastos.service.tipo;

import com.dkinkel.gastos.model.tipo.TipoGasto;
import com.dkinkel.gastos.repository.tipo.TipoGastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoGastoService {

    @Autowired
    private TipoGastoRepository tipoGastoRepository;

    public List<TipoGasto> getAllTiposGasto() {
        return tipoGastoRepository.findAll();
    }

    public Optional<TipoGasto> getTipoGastoById(Long id) {
        return tipoGastoRepository.findById(id);
    }

    public TipoGasto saveTipoGasto(TipoGasto tipoGasto) {
        return tipoGastoRepository.save(tipoGasto);
    }

    public void deleteTipoGasto(Long id) {
        tipoGastoRepository.deleteById(id);
    }

    public TipoGasto updateTipoGasto(Long id, TipoGasto tipoGasto) {
        if (tipoGastoRepository.existsById(id)) {
            tipoGasto.setId(id);
            return tipoGastoRepository.save(tipoGasto);
        }
        return null;
    }

    public List<TipoGasto> findByActivoTrue() {
        return tipoGastoRepository.findByActivoTrue();
    }
}
