package com.rajesh.dws.dto;

import java.math.BigDecimal;

public class AddMoneyRequest {

    private String email;
    private BigDecimal amount;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}