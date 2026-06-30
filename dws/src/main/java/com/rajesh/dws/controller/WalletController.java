package com.rajesh.dws.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import com.rajesh.dws.dto.DepositRequest;
import com.rajesh.dws.dto.TransferRequest;
import com.rajesh.dws.dto.WalletStatsResponse;
import com.rajesh.dws.dto.WithdrawalRequestDto;
import com.rajesh.dws.service.WalletService;
import java.math.BigDecimal;
// import java.time.LocalDateTime;
import java.util.List;
import com.rajesh.dws.entity.Transaction;
import com.rajesh.dws.entity.WithdrawalRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;
    @GetMapping("/balance")
    public BigDecimal getBalance(Authentication authentication) {
        return walletService.getBalance(authentication.getName());
    }
    // @PostMapping("/deposit")
    // public BigDecimal deposit(Authentication authentication,@RequestBody DepositRequest request) {
    //     return walletService.deposit(authentication.getName(),request.getAmount());
    // }
    @PostMapping("/transfer")
    public String transfer(Authentication authentication,@RequestBody TransferRequest request) {
        walletService.transfer(authentication.getName(),request.getReceiverEmail(),request.getAmount());
        return "Transfer Successful";
    }
    @PostMapping("/withdraw")
public String withdraw(
        Authentication authentication,
        @RequestBody
        WithdrawalRequestDto request) {

    return walletService.requestWithdrawal(
            authentication.getName(),
            request.getAmount()
    );
}
@GetMapping("/withdrawals")
public List<WithdrawalRequest>
withdrawals(
        Authentication authentication) {

    return walletService
            .getWithdrawals(
                    authentication.getName()
            );
}
    @GetMapping("/history")
    public List<Transaction> history(Authentication authentication) {
        return walletService.getHistory(authentication.getName());
    }
    @GetMapping("/stats")
public WalletStatsResponse stats(
        Authentication authentication) {

    return walletService.getStats(
            authentication.getName()
    );
}
}