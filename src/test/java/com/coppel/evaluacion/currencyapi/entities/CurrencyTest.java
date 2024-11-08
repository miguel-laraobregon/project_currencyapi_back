package com.coppel.evaluacion.currencyapi.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {

    private Long id;
    private String code;
    private String name;


    @BeforeEach
    void setUp() {
        id = 1L;
        code = "MXN";
        name = "Peso mexicano";
    }

    @Test
    void constructor() {
        Currency currency = new Currency(id, code, name);
        assertEquals(id, currency.getId());
        assertEquals(code, currency.getCode());
        assertEquals(name, currency.getName());

    }

    @Test
    void settersAndGetters() {
        Currency currency = new Currency();
        currency.setId(id);
        currency.setCode(code);
        currency.setName(name);

        assertEquals(id, currency.getId());
        assertEquals(code, currency.getCode());
        assertEquals(name, currency.getName());
    }
}