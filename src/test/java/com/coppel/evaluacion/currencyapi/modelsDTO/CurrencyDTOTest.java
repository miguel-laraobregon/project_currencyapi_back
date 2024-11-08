package com.coppel.evaluacion.currencyapi.modelsDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyDTOTest {

    @Test
    void constructor() {
        CurrencyDTO currencyDTO = new CurrencyDTO(1L, "MXN");
        assertEquals(1L, currencyDTO.getId());
        assertEquals("MXN", currencyDTO.getCode());
    }

    @Test
    void settersAndGetters() {
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setId(1L);
        currencyDTO.setCode("MXN");

        assertEquals(1L, currencyDTO.getId());
        assertEquals("MXN", currencyDTO.getCode());
    }

}