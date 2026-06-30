package com.rajesh.dws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rajesh.dws.dto.ChangePasswordRequest;
import com.rajesh.dws.dto.ProfileResponse;
import com.rajesh.dws.entity.User;
import com.rajesh.dws.repository.UserRepository;
// import com.rajesh.dws.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    // @Autowired
    // private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ProfileResponse getProfile(String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        return new ProfileResponse(
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
    public String changePassword(
        String email,
        ChangePasswordRequest request) {

    User user =
            userRepository
                    .findByEmail(email)
                    .orElseThrow();

    boolean matches =
            passwordEncoder.matches(
                    request.getCurrentPassword(),
                    user.getPassword()
            );

    if(!matches) {

        throw new RuntimeException(
                "Current password incorrect"
        );
    }

    user.setPassword(
            passwordEncoder.encode(
                    request.getNewPassword()
            )
    );

    userRepository.save(user);

    // emailService.sendEmail(
    //         user.getEmail(),
    //         "Password Changed",
    //         """
    //         Hello,

    //         Your password was changed successfully.

    //         If this wasn't you,
    //         contact support immediately.

    //         Digital Wallet Team
    //         """
    // );

    return "Password Changed";
}
}