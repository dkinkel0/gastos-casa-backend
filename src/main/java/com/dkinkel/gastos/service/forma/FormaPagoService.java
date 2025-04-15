package com.dkinkel.gastos.service.forma;

import com.dkinkel.gastos.model.forma.FormaPago;
import com.dkinkel.gastos.repository.forma.FormaPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormaPagoService {

    @Autowired
    private FormaPagoRepository formaPagoRepository;

    public List<FormaPago> getAllFormasPago() {
        return formaPagoRepository.findAll();
    }

    public Optional<FormaPago> getFormaPagoById(Long id) {
        return formaPagoRepository.findById(id);
    }

    public FormaPago saveFormaPago(FormaPago formaPago) {
        return formaPagoRepository.save(formaPago);
    }

    public void deleteFormaPago(Long id) {
        formaPagoRepository.deleteById(id);
    }

    public FormaPago updateFormaPago(Long id, FormaPago formaPago) {
        if (formaPagoRepository.existsById(id)) {
            formaPago.setId(id);
            return formaPagoRepository.save(formaPago);
        }
        return null;
    }
}
