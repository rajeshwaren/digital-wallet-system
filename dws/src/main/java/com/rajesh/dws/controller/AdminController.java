package com.rajesh.dws.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.rajesh.dws.entity.*;
import com.rajesh.dws.service.AdminService;
import com.rajesh.dws.dto.AddMoneyRequest;
import com.rajesh.dws.dto.AdminStatsResponse;
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }
    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return adminService.getAllTransactions();
    }
    @PostMapping("/freeze/{id}")
    public String freezeUser(@PathVariable Long id) {
    adminService.freezeUser(id);
    return "User Frozen";
    }
    @GetMapping("/withdrawals")
    public List<WithdrawalRequest> withdrawals() {
        return adminService.getWithdrawalRequests();
    }
    @PostMapping("/withdrawals/approve/{id}")
    public String approve(@PathVariable Long id) {
        adminService.approveWithdrawal(id);
        return "Approved";
    }
    @PostMapping("/withdrawals/reject/{id}")
    public String reject(@PathVariable Long id) {
        adminService.rejectWithdrawal(id);
        return "Rejected";
    }
    @PostMapping("/unfreeze/{id}")
    public String unfreezeUser(@PathVariable Long id) {
        adminService.unfreezeUser(id);
        return "User Activated";
    }
    @PostMapping("/add-money")
    public String addMoney(@RequestBody AddMoneyRequest request) {
        adminService.addMoney(request.getEmail(),request.getAmount());
        return "Money Added Successfully";
    }
    @GetMapping("/stats")
    public AdminStatsResponse getStats() {
        return adminService.getStats();
    }
}