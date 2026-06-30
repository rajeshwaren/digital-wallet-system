package com.rajesh.dws.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rajesh.dws.dto.AdminStatsResponse;
import com.rajesh.dws.entity.*;
import com.rajesh.dws.repository.TransactionRepository;
import com.rajesh.dws.repository.UserRepository;
import com.rajesh.dws.repository.WithdrawalRequestRepository;

import jakarta.transaction.Transactional;


@Service
public class AdminService {
    // @Autowired
    // private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private WithdrawalRequestRepository withdrawalRepository;
    @Autowired
    private NotificationService notificationService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public List<Transaction> getAllTransactions() {

        return transactionRepository.findAll();
    }
    public List<WithdrawalRequest> getWithdrawalRequests() {

       return withdrawalRepository.findAll();
    }
    @Transactional
    public void approveWithdrawal(Long requestId) {

    WithdrawalRequest request =withdrawalRepository.findById(requestId).orElseThrow();

    User user = userRepository
            .findByEmail(request.getUserEmail())
            .orElseThrow();

    user.setBalance(
            user.getBalance()
                    .subtract(
                            request.getAmount()
                    )
    );

    request.setStatus("APPROVED");

    userRepository.save(user);

    withdrawalRepository.save(request);
    notificationService.createNotification(
        request.getUserEmail(),
        "Withdrawal Approved"
        
    );

    }
    public void rejectWithdrawal(
        Long requestId) {

    WithdrawalRequest request =
            withdrawalRepository
                    .findById(requestId)
                    .orElseThrow();

    request.setStatus("REJECTED");

    withdrawalRepository.save(request);
    notificationService.createNotification(
        request.getUserEmail(),
        "Withdrawal Rejected"
    );
    }
    @Transactional
    public void addMoney(
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

    Transaction transaction =
            new Transaction();

    transaction.setSenderEmail(
            "ADMIN"
    );

    transaction.setReceiverEmail(
            email
    );

    transaction.setAmount(amount);

    transaction.setType(
            "ADMIN_CREDIT"
    );

    transaction.setStatus(
            "SUCCESS"
    );

    transaction.setCreatedAt(
            java.time.LocalDateTime.now()
    );

    transactionRepository.save(
            transaction
    );
    notificationService.createNotification(
        user.getEmail(),
        "Admin added ₹" + amount
    );
    }
    public void freezeUser(Long id) {

    User user =
            userRepository.findById(id)
                    .orElseThrow();

    user.setStatus("FROZEN");
    //     emailService.sendEmail(
    //         user.getEmail(),
    //         "Account Frozen",
    //         "Your Digital Wallet account has been frozen by an administrator."
    // );

    userRepository.save(user);
    notificationService.createNotification(
        user.getEmail(),
        "Account Frozen"

    );
    }
    public void unfreezeUser(Long id) {

    User user =
            userRepository.findById(id)
                    .orElseThrow();
        

    user.setStatus("ACTIVE");


    userRepository.save(user);
    // emailService.sendEmail(
    //     user.getEmail(),
    //     "Account Activated",
    //     "Your Digital Wallet account has been reactivated."
    // );
    }
    public AdminStatsResponse getStats() {

    long totalUsers =
            userRepository.count();

    long totalTransactions =
            transactionRepository.count();

    BigDecimal totalMoney =
            userRepository.findAll()
                    .stream()
                    .map(User::getBalance)
                    .reduce(
                            BigDecimal.ZERO,
                            BigDecimal::add
                    );
        

    return new AdminStatsResponse(
            totalUsers,
            totalTransactions,
            totalMoney
    );
    }
}