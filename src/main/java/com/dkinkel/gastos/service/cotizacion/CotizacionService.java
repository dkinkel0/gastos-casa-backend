package com.dkinkel.gastos.service.cotizacion;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.dkinkel.gastos.model.cotizacion.CotizacionDolar;
import com.dkinkel.gastos.repository.cotizacion.CotizacionRepository;

@Service
public class CotizacionService {
    @Autowired
    private CotizacionRepository cotizacionRepository;
    
    public List<CotizacionDolar> findAll() {
        return cotizacionRepository.findByOrderByFechaDesc();
    }
    
    public CotizacionDolar save(CotizacionDolar cotizacion) {
        return cotizacionRepository.save(cotizacion);
    }
    
    public Optional<CotizacionDolar> findById(Long id) {
        return cotizacionRepository.findById(id);
    }
    
    public void deleteById(Long id) {
        cotizacionRepository.deleteById(id);
    }

    public CotizacionDolar update(Long id, CotizacionDolar cotizacionActualizada) {
        return cotizacionRepository.findById(id)
            .map(cotizacionExistente -> {
                cotizacionExistente.setFecha(cotizacionActualizada.getFecha());
                cotizacionExistente.setPrecioCompra(cotizacionActualizada.getPrecioCompra());
                cotizacionExistente.setPrecioVenta(cotizacionActualizada.getPrecioVenta());
                cotizacionExistente.setPrecioIntermedio(cotizacionActualizada.getPrecioIntermedio());
                return cotizacionRepository.save(cotizacionExistente);
            })
            .orElseGet(() -> {
                cotizacionActualizada.setId(id);
                return cotizacionRepository.save(cotizacionActualizada);
            });
    }

    public Optional<CotizacionDolar> findByFecha(LocalDate fecha) {
        return cotizacionRepository.findByFecha(fecha);
    }
}
