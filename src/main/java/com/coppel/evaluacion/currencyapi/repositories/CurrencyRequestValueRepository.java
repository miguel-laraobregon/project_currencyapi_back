package com.coppel.evaluacion.currencyapi.repositories;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.coppel.evaluacion.currencyapi.entities.CurrencyRequestValue;

public interface CurrencyRequestValueRepository extends JpaRepository<CurrencyRequestValue, Long>{


    Page<CurrencyRequestValue> findByCurrency_CodeAndCurrencyRequest_CreatedAtBetween(
            String currencyCode, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);


    Page<CurrencyRequestValue> findByCurrencyRequest_CreatedAtBetween(
            LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

}
