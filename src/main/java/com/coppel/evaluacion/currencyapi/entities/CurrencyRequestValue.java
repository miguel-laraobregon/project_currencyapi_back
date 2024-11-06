package com.coppel.evaluacion.currencyapi.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="currency_requests_values")
public class CurrencyRequestValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CurrencyRequest currencyRequest;
    
    @ManyToOne
    private Currency currency;

    @Column(precision = 18, scale = 10)  // Correspondiente a DECIMAL(18, 10)
    private BigDecimal value;

    public CurrencyRequestValue() {
    }

    public CurrencyRequestValue(Long id, CurrencyRequest currencyRequest, Currency currency, BigDecimal value) {
        this.id = id;
        this.currencyRequest = currencyRequest;
        this.currency = currency;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CurrencyRequest getCurrencyRequest() {
        return currencyRequest;
    }

    public void setCurrencyRequest(CurrencyRequest currencyRequest) {
        this.currencyRequest = currencyRequest;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    

}
