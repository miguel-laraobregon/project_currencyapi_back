package com.coppel.evaluacion.currencyapi.modelsDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import static org.junit.jupiter.api.Assertions.*;

class CurrencyRequestValueDTOTest {

    private Long id;
    private String code;
    private BigDecimal value;
    private LocalDateTime lastUpdatedAt;
    private LocalDateTime createdAt;

    @BeforeEach
    void setUp() {
        id = 1L;
        code = "MXN";
        value = new BigDecimal("20.45");
        lastUpdatedAt = LocalDateTime.parse("2024-11-06T23:59:59", DateTimeFormatter.ISO_DATE_TIME);
        createdAt = LocalDateTime.parse("2024-11-08T23:59:59", DateTimeFormatter.ISO_DATE_TIME);

    }

    @Test
    void constructor() {
        CurrencyRequestValueDTO dto = new CurrencyRequestValueDTO(id, code, value, lastUpdatedAt, createdAt);
        assertEquals(id, dto.getId());
        assertEquals(code, dto.getCode());
        assertEquals(value, dto.getValue());
        assertEquals(lastUpdatedAt, dto.getLastUpdatedAt());
        assertEquals(createdAt, dto.getCreatedAt());
    }

    @Test
    void settersAndGetters() {
        CurrencyRequestValueDTO dto = new CurrencyRequestValueDTO();

        dto.setId(id);
        dto.setCode(code);
        dto.setValue(value);
        dto.setLastUpdatedAt(lastUpdatedAt);
        dto.setCreatedAt(createdAt);

        assertEquals(id, dto.getId());
        assertEquals(code, dto.getCode());
        assertEquals(value, dto.getValue());
        assertEquals(lastUpdatedAt, dto.getLastUpdatedAt());
        assertEquals(createdAt, dto.getCreatedAt());
    }

}