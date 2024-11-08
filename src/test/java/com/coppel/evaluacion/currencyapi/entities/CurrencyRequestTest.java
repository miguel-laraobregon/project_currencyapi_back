package com.coppel.evaluacion.currencyapi.entities;

import com.coppel.evaluacion.currencyapi.modelsDTO.CurrencyDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyRequestTest {


    private Long id;
    private String status;
    private LocalDateTime lastUpdatedAt;
    private float responseTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @BeforeEach
    void setUp() {
        id = 1L;
        status = "COMPLETED";
        lastUpdatedAt = LocalDateTime.parse("2024-11-06T23:59:59", DateTimeFormatter.ISO_DATE_TIME);
        responseTime = 123.45f;
        createdAt = LocalDateTime.parse("2024-11-08T23:59:59", DateTimeFormatter.ISO_DATE_TIME);
        updatedAt = LocalDateTime.parse("2024-11-08T23:59:59", DateTimeFormatter.ISO_DATE_TIME);
    }

    @Test
    void constructor() {
        CurrencyRequest currencyRequest = new CurrencyRequest(id, status, lastUpdatedAt, responseTime,
        createdAt, updatedAt);
        assertEquals(id, currencyRequest.getId());
        assertEquals(status, currencyRequest.getStatus());
        assertEquals(lastUpdatedAt, currencyRequest.getLastUpdatedAt());
        assertEquals(responseTime, currencyRequest.getResponseTime());
        assertEquals(createdAt, currencyRequest.getCreatedAt());
        assertEquals(updatedAt, currencyRequest.getUpdatedAt());

    }

    @Test
    void settersAndGetters() {
        CurrencyRequest currencyRequest = new CurrencyRequest();
        currencyRequest.setId(id);
        currencyRequest.setStatus(status);
        currencyRequest.setLastUpdatedAt(lastUpdatedAt);
        currencyRequest.setResponseTime(responseTime);
        currencyRequest.setCreatedAt(createdAt);
        currencyRequest.setUpdatedAt(updatedAt);

        assertEquals(id, currencyRequest.getId());
        assertEquals(status, currencyRequest.getStatus());
        assertEquals(lastUpdatedAt, currencyRequest.getLastUpdatedAt());
        assertEquals(responseTime, currencyRequest.getResponseTime());
        assertEquals(createdAt, currencyRequest.getCreatedAt());
        assertEquals(updatedAt, currencyRequest.getUpdatedAt());
    }
}