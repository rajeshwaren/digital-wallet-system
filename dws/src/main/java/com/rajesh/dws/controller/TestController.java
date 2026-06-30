package com.rajesh.dws.controller;
import org.springframework.web.bind.annotation.RestController;

import com.rajesh.dws.service.EmailService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/hello")
    public String hello() {
        return "JWT Works!";
    }
    @Autowired
private EmailService emailService;

@GetMapping("/test-email")
public String testEmail() {

    emailService.sendEmail(
            "rajeshwaren2005@gmail.com",
            "Digital Wallet Test",
            "Email service working!"
    );

    return "Email Sent";
}
}
