package com.coppel.evaluacion.currencyapi.modelsDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CurrencyRequestValueDTO {

    private Long id;
    private String code;
    private BigDecimal value;
    private LocalDateTime lastUpdatedAt;
    private LocalDateTime createdAt;


    public CurrencyRequestValueDTO() {
    }

    public CurrencyRequestValueDTO(Long id, String code, BigDecimal value, LocalDateTime lastUpdatedAt,
            LocalDateTime createdAt) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.lastUpdatedAt = lastUpdatedAt;
        this.createdAt = createdAt;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public BigDecimal getValue() {
        return value;
    }
    public void setValue(BigDecimal value) {
        this.value = value;
    }
    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }
    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
