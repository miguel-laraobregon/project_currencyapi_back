package com.coppel.evaluacion.currencyapi.controllers;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coppel.evaluacion.currencyapi.entities.CurrencyRequestValue;
import com.coppel.evaluacion.currencyapi.entities.Currency;
import com.coppel.evaluacion.currencyapi.modelsDTO.CurrencyDTO;
import com.coppel.evaluacion.currencyapi.modelsDTO.CurrencyRequestValueDTO;
import com.coppel.evaluacion.currencyapi.services.CurrencyRequestValueService;
import com.coppel.evaluacion.currencyapi.services.CurrencyService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;
    
    @Autowired
    private CurrencyRequestValueService currencyRequestValueService;

    @GetMapping
    public ResponseEntity<Object> getCurrencies(){

        List<Currency> result = currencyService.getCurrencies();

        List<CurrencyDTO> data = result.stream().map(currency -> new CurrencyDTO(
            currency.getId(),
            currency.getCode()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(data);
    }


    @GetMapping("/{currencyCode}")
    public ResponseEntity<Object> getCurrencies( @PathVariable String currencyCode,  @RequestParam(required = false) String finit,  @RequestParam(required = false) String fend, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){

        try {
            Page <CurrencyRequestValue> result = currencyRequestValueService.getCurrencyRequestValues(currencyCode, finit, fend, page, size);

            // Convertimos a DTO ( Data Transfer Object )
            Page<CurrencyRequestValueDTO> data = result.map(currencyRequestValue -> new CurrencyRequestValueDTO(
                    currencyRequestValue.getId(),
                    currencyRequestValue.getCurrency().getCode(),
                    currencyRequestValue.getValue(),
                    currencyRequestValue.getCurrencyRequest().getLastUpdatedAt(),
                    currencyRequestValue.getCurrencyRequest().getCreatedAt()
            ));

            return ResponseEntity.ok(data);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

}
