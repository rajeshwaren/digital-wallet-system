package com.rajesh.dws.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.rajesh.dws.service.AuthService;
import com.rajesh.dws.dto.LoginRequest;
import com.rajesh.dws.dto.LoginResponse;
import com.rajesh.dws.dto.RegisterRequest;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public String register( @RequestBody RegisterRequest request) {
        return authService.register(request);
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}