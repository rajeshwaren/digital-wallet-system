package com.rajesh.dws.dto;

import java.math.BigDecimal;

public class AdminStatsResponse {

    private long totalUsers;
    private long totalTransactions;
    private BigDecimal totalMoney;

    public AdminStatsResponse(
            long totalUsers,
            long totalTransactions,
            BigDecimal totalMoney) {

        this.totalUsers = totalUsers;
        this.totalTransactions = totalTransactions;
        this.totalMoney = totalMoney;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public long getTotalTransactions() {
        return totalTransactions;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }
}