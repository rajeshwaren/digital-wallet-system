package com.rajesh.dws.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WithdrawalRequestDto {

    private BigDecimal amount;
}