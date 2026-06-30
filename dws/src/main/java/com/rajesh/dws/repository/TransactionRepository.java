package com.rajesh.dws.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rajesh.dws.entity.Transaction;
public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderEmailOrReceiverEmail(
            String senderEmail,
            String receiverEmail
    );
}