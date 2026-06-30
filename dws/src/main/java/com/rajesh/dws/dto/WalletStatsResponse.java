package com.rajesh.dws.dto;

import java.math.BigDecimal;

public class WalletStatsResponse {

    private BigDecimal totalDeposits;
    private BigDecimal totalTransfers;
    private Long transactionCount;

    public WalletStatsResponse(
            BigDecimal totalDeposits,
            BigDecimal totalTransfers,
            Long transactionCount) {

        this.totalDeposits = totalDeposits;
        this.totalTransfers = totalTransfers;
        this.transactionCount = transactionCount;
    }

    public BigDecimal getTotalDeposits() {
        return totalDeposits;
    }

    public BigDecimal getTotalTransfers() {
        return totalTransfers;
    }

    public Long getTransactionCount() {
        return transactionCount;
    }
}