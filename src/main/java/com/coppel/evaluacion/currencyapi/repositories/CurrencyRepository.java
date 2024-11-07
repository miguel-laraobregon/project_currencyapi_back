package com.coppel.evaluacion.currencyapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coppel.evaluacion.currencyapi.entities.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByCode(String code);
}

