package com.coppel.evaluacion.currencyapi.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.coppel.evaluacion.currencyapi.entities.Currency;
import com.coppel.evaluacion.currencyapi.entities.CurrencyRequestValue;
import com.coppel.evaluacion.currencyapi.repositories.CurrencyRepository;
import com.coppel.evaluacion.currencyapi.repositories.CurrencyRequestRepository;
import com.coppel.evaluacion.currencyapi.repositories.CurrencyRequestValueRepository;

@Service
public class CurrencyRequestValueService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyRequestRepository currencyRequestRepository;

    @Autowired
    private CurrencyRequestValueRepository currencyRequestValueRepository;

    

    public Page<CurrencyRequestValue> getCurrencyRequestValues(String currencyCode, String finit, String fend, int page, int size){

        // Validamos valor de currencyCode
        if (currencyCode.length() != 3) {
            throw new IllegalArgumentException("La clave de divisa debe ser de 3 caracteres");
        }

        // validamos que el valor de currencyCode exista en el catalogo de currencies
        if (!"all".equals(currencyCode)) {
            Currency currency = currencyRepository.findByCode(currencyCode);
            if (currency == null) {
                throw new IllegalArgumentException("No se encontro la clave de divisa en el catalogo.");
            }
        }

        // Validamos las fechas
        LocalDateTime startDate = finit == null ? currencyRequestRepository.findTop1ByOrderByCreatedAtAsc().getCreatedAt() : null; 
        LocalDateTime endDate = fend == null ? currencyRequestRepository.findTop1ByOrderByCreatedAtDesc().getCreatedAt() : null; 
        
        try {
            if (finit != null) {
                startDate = LocalDateTime.parse(finit, DateTimeFormatter.ISO_DATE_TIME);
            } 
            if (fend != null) {
                endDate = LocalDateTime.parse(fend, DateTimeFormatter.ISO_DATE_TIME);
            } 
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de fecha invalido. Use 'YYYY-MM-DDThh:mm:ss'.");
        }

        Pageable pageable = PageRequest.of(page, size);

        
        // Obtenemos resultados de todas las divisas
        if ("all".equals(currencyCode)) {
            return currencyRequestValueRepository.findByCurrencyRequest_CreatedAtBetween(startDate, endDate, pageable);
        }
        
        // Obtenemos resultados solo de una divisa
        return currencyRequestValueRepository.findByCurrency_CodeAndCurrencyRequest_CreatedAtBetween(currencyCode, startDate, endDate, pageable);
    }

}
