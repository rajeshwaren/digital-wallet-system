package com.rajesh.dws.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rajesh.dws.entity.User;
import com.rajesh.dws.entity.WithdrawalRequest;
import com.rajesh.dws.repository.UserRepository;
import com.rajesh.dws.repository.WithdrawalRequestRepository;

import jakarta.transaction.Transactional;

import java.util.List;
// import com.rajesh.dws.service.NotificationService;
import com.rajesh.dws.dto.WalletStatsResponse;
import com.rajesh.dws.entity.Transaction;
import com.rajesh.dws.repository.TransactionRepository;

@Service
public class WalletService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private WithdrawalRequestRepository withdrawalRepository;
    @Autowired
private EmailService emailService;
    public BigDecimal getBalance(String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        return user.getBalance();
    }
    public BigDecimal deposit(
        String email,
        BigDecimal amount) {

                if(amount.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new RuntimeException(
                                "Amount must be greater than zero"
                        );
                }

    User user = userRepository
            .findByEmail(email)
            .orElseThrow();

    user.setBalance(
            user.getBalance().add(amount)
    );

    userRepository.save(user);
    Transaction transaction = new Transaction();

transaction.setSenderEmail("SYSTEM");
transaction.setReceiverEmail(email);
transaction.setAmount(amount);
transaction.setType("DEPOSIT");
transaction.setStatus("SUCCESS");
transaction.setCreatedAt(LocalDateTime.now());

transactionRepository.save(transaction);

    return user.getBalance();
}
public WalletStatsResponse getStats(String email) {

    List<Transaction> transactions =
            transactionRepository
            .findBySenderEmailOrReceiverEmail(
                    email,
                    email
            );

    BigDecimal totalDeposits =
            transactions.stream()
                    .filter(t ->
                            "DEPOSIT".equals(
                                    t.getType()))
                    .map(Transaction::getAmount)
                    .reduce(
                            BigDecimal.ZERO,
                            BigDecimal::add
                    );

    BigDecimal totalTransfers =
            transactions.stream()
                    .filter(t ->
                            "TRANSFER".equals(
                                    t.getType()))
                    .map(Transaction::getAmount)
                    .reduce(
                            BigDecimal.ZERO,
                            BigDecimal::add
                    );

    return new WalletStatsResponse(
            totalDeposits,
            totalTransfers,
            (long) transactions.size()
    );
}
public String requestWithdrawal(
        String email,
        BigDecimal amount) {

    User user = userRepository
            .findByEmail(email)
            .orElseThrow();

    if(user.getBalance().compareTo(amount) < 0) {

        throw new RuntimeException(
                "Insufficient Balance"
        );
    }

    WithdrawalRequest request =
            new WithdrawalRequest();

    request.setUserEmail(email);
    request.setAmount(amount);
    request.setStatus("PENDING");
    request.setCreatedAt(
            LocalDateTime.now()
    );

    withdrawalRepository.save(request);
    emailService.sendEmail(
        user.getEmail(),
        "Withdrawal Request Submitted",
        "Your withdrawal request for ₹" +
        amount +
        " has been submitted and is awaiting admin approval."
);

    return "Withdrawal Request Submitted";
}
public List<WithdrawalRequest>
getWithdrawals(String email) {

    return withdrawalRepository
            .findByUserEmail(email);
}

@Transactional
public void transfer(
        String senderEmail,
        String receiverEmail,
        BigDecimal amount) {
                
                if(amount.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new RuntimeException(
                                "Amount must be greater than zero"
                        );
                }


                if(senderEmail.equals(receiverEmail)) {
                        throw new RuntimeException(
                                "Cannot transfer to yourself"
                        );
                }

    User sender = userRepository
            .findByEmail(senderEmail)
            .orElseThrow();

    User receiver = userRepository
            .findByEmail(receiverEmail)
            .orElseThrow();
            if(!sender.getStatus().equals("ACTIVE")) {

    throw new RuntimeException(
            "Account is frozen"
    );
}

    if(sender.getBalance().compareTo(amount) < 0) {
        throw new RuntimeException(
                "Insufficient Balance"
        );
    }

    sender.setBalance(
            sender.getBalance().subtract(amount)
    );

    receiver.setBalance(
            receiver.getBalance().add(amount)
    );
    Transaction transaction = new Transaction();

transaction.setSenderEmail(senderEmail);
transaction.setReceiverEmail(receiverEmail);
transaction.setAmount(amount);
transaction.setType("TRANSFER");
transaction.setStatus("SUCCESS");
transaction.setCreatedAt(LocalDateTime.now());

transactionRepository.save(transaction);

    userRepository.save(sender);
    userRepository.save(receiver);
    notificationService.createNotification(
        receiver.getEmail(),
        "Received ₹" + amount
);
notificationService.createNotification(
        sender.getEmail(),
        "Sent ₹" + amount
);
// emailService.sendEmail(
//         receiver.getEmail(),
//         "Money Received",
//         "You have received ₹" +
//         amount +
//         " from " +
//         sender.getName()
// );
// emailService.sendEmail(
//         sender.getEmail(),
//         "Money Sent",
//         "You have successfully sent ₹" +
//         amount +
//         " to " +
//         receiver.getEmail()
// );
}
public List<Transaction> getHistory(String email) {

    return transactionRepository
            .findBySenderEmailOrReceiverEmail(
                    email,
                    email
            );
}

}