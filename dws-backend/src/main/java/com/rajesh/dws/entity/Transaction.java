package com.rajesh.dws.entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String senderEmail;
    private String receiverEmail;
    private BigDecimal amount;
    private String type;
    private String status;
    private LocalDateTime createdAt;
}