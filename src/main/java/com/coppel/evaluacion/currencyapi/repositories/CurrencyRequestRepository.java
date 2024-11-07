package com.coppel.evaluacion.currencyapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coppel.evaluacion.currencyapi.entities.CurrencyRequest;

public interface CurrencyRequestRepository extends JpaRepository<CurrencyRequest, Long> {

    CurrencyRequest findTop1ByOrderByCreatedAtDesc();

    CurrencyRequest findTop1ByOrderByCreatedAtAsc();
}
