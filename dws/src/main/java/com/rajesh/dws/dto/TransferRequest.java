package com.rajesh.dws.dto;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class TransferRequest {

    private String receiverEmail;
    private BigDecimal amount;
    
}