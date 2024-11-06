package com.coppel.evaluacion.currencyapi.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="currency_requests")
public class CurrencyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    
    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdateAt;
    
    @Column(name = "response_time")
    private float responseTime;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    public CurrencyRequest() {
    }
    public CurrencyRequest(Long id, String status, LocalDateTime lastUpdateAt, float responseTime,
            LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.status = status;
        this.lastUpdateAt = lastUpdateAt;
        this.responseTime = responseTime;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getLastUpdateAt() {
        return lastUpdateAt;
    }
    public void setLastUpdateAt(LocalDateTime lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }
    public float getResponseTime() {
        return responseTime;
    }
    public void setResponseTime(float responseTime) {
        this.responseTime = responseTime;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    

}
