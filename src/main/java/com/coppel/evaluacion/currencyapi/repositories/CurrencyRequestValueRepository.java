package com.coppel.evaluacion.currencyapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coppel.evaluacion.currencyapi.entities.CurrencyRequestValue;

public interface CurrencyRequestValueRepository extends JpaRepository<CurrencyRequestValue, Long>{

}
