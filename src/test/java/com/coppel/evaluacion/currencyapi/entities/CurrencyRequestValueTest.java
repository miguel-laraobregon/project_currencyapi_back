package com.coppel.evaluacion.currencyapi.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyRequestValueTest {

    private Long id;
    private CurrencyRequest currencyRequest;
    private Currency currency;
    private BigDecimal value;

    @BeforeEach
    void setUp() {

        id = 1L;
        value = BigDecimal.valueOf(19.22);

        currency = new Currency(1L, "MXN", "");

        currencyRequest = new CurrencyRequest();
        currencyRequest.setCreatedAt(LocalDateTime.now());
        currencyRequest.setStatus("COMPLETED");

    }

    @Test
    void constructor() {
        CurrencyRequestValue c = new CurrencyRequestValue(id, currencyRequest, currency, value);
        assertEquals(id, c.getId());
        assertEquals(currencyRequest, c.getCurrencyRequest());
        assertEquals(currency, c.getCurrency());
        assertEquals(value, c.getValue());

        assertEquals("CurrencyRequestValue [currencyRequest=" + currencyRequest + ", currency=" + currency
                + ", value=" + value + "]", c.toString());

    }

    @Test
    void settersAndGetters() {
        CurrencyRequestValue c = new CurrencyRequestValue();
        c.setId(id);
        c.setCurrencyRequest(currencyRequest);
        c.setCurrency(currency);
        c.setValue(value);

        assertEquals(id, c.getId());
        assertEquals(currencyRequest, c.getCurrencyRequest());
        assertEquals(currency, c.getCurrency());
        assertEquals(value, c.getValue());
    }


}