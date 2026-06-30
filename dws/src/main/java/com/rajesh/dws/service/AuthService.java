package com.rajesh.dws.service;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.rajesh.dws.repository.UserRepository;
import com.rajesh.dws.dto.LoginRequest;
import com.rajesh.dws.dto.LoginResponse;
import com.rajesh.dws.dto.RegisterRequest;
import com.rajesh.dws.entity.User;
// import java.util.Optional;
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
private EmailService emailService;
    public String register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists";
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setBalance(BigDecimal.ZERO);
        user.setRole("USER");
        user.setStatus("ACTIVE");
        String encodedPassword =passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        emailService.sendWelcomeEmail(user.getEmail(),user.getName());
        return "Registration Successful";
    }

    // public String login(LoginRequest request) {

    //     Optional<User> optionalUser =
    //             userRepository.findByEmail(request.getEmail());

    //     if(optionalUser.isEmpty()) {
    //         return "User not found";
    //     }

    //     User user = optionalUser.get();

    //     boolean validPassword =
    //             passwordEncoder.matches(
    //                     request.getPassword(),
    //                     user.getPassword()
    //             );

    //     if(!validPassword) {
    //         return "Invalid password";
    //     }

    //     return "Login Successful";
    // }
    public LoginResponse login(LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
    boolean valid =passwordEncoder.matches(request.getPassword(),user.getPassword());
    if(!valid) {
        throw new RuntimeException("Invalid Password");
    }
    String token =jwtService.generateToken(user.getEmail(),user.getRole());
    return new LoginResponse(token);
}

}