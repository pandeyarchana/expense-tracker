package com.archana.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Balance {
    private Long userId;
    private Double amount;

    public Balance(Long userId, Double amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
