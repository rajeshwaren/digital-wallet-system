package com.rajesh.dws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(
            String to,
            String subject,
            String body) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
    public void sendWelcomeEmail(
            String email,
            String name) {

        sendEmail(
                email,
                "Welcome to Digital Wallet",
                """
                Hello %s,

                Welcome to Digital Wallet.

                Your account has been created successfully.

                You can now:
                • Transfer money
                • Scan QR codes
                • Request withdrawals
                • Download statements

                Thank you for choosing Digital Wallet.

                Digital Wallet Team
                """.formatted(name)
        );
    }
    public void sendMoneyReceivedEmail(
        String email,
        String senderName,
        double amount) {

    sendEmail(
            email,
            "Money Received",
            """
            Hello,

            You have received ₹%.2f from %s.

            The amount has been credited to your wallet.

            Digital Wallet Team
            """
            .formatted(amount, senderName)
    );
}
public void sendMoneySentEmail(
        String email,
        String receiverEmail,
        double amount) {

    sendEmail(
            email,
            "Money Transfer Successful",
            """
            Hello,

            You have successfully sent ₹%.2f to %s.

            Thank you for using Digital Wallet.

            Digital Wallet Team
            """
            .formatted(amount, receiverEmail)
    );
}
public void sendWithdrawalApprovedEmail(
        String email,
        double amount) {

    sendEmail(
            email,
            "Withdrawal Approved",
            """
            Hello,

            Your withdrawal request for ₹%.2f
            has been approved.

            The amount has been deducted
            from your wallet balance.

            Digital Wallet Team
            """
            .formatted(amount)
    );
}
public void sendWithdrawalRejectedEmail(
        String email,
        double amount) {

    sendEmail(
            email,
            "Withdrawal Rejected",
            """
            Hello,

            Your withdrawal request for ₹%.2f
            has been rejected.

            Please contact support if you
            need additional information.

            Digital Wallet Team
            """
            .formatted(amount)
    );
}
public void sendAccountFrozenEmail(
        String email) {

    sendEmail(
            email,
            "Account Frozen",
            """
            Hello,

            Your account has been frozen
            by an administrator.

            Wallet operations are temporarily
            unavailable.

            Digital Wallet Team
            """
    );
}
public void sendAccountActivatedEmail(
        String email) {

    sendEmail(
            email,
            "Account Reactivated",
            """
            Hello,

            Your account has been reactivated.

            You can now access all wallet
            services again.

            Digital Wallet Team
            """
    );
}
}