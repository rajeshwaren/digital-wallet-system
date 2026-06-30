package com.rajesh.dws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajesh.dws.dto.ChangePasswordRequest;
import com.rajesh.dws.dto.ProfileResponse;
import com.rajesh.dws.service.StatementService;
import com.rajesh.dws.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StatementService statementService;
    @GetMapping("/statement")
    public ResponseEntity<byte[]> downloadStatement(Authentication auth) {
    byte[] pdf =statementService.generateStatement(auth.getName());
    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=statement.pdf"
            )
            .contentType(
                    MediaType.APPLICATION_PDF
            )
            .body(pdf);
}
    @GetMapping("/profile")
    public ProfileResponse profile(
            Authentication authentication) {
                
        return userService.getProfile(
                authentication.getName()
        );
    }
    @PostMapping("/change-password")
public String changePassword(
        Authentication auth,
        @RequestBody
        ChangePasswordRequest request) {

    return userService.changePassword(
            auth.getName(),
            request
    );
}
}