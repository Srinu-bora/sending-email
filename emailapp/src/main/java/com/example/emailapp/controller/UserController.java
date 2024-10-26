// package com.example.emailapp.controller;
// import com.example.emailapp.model.User;
// import com.example.emailapp.repository.UserRepository;

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import com.example.emailapp.service.*;

// import jakarta.validation.Valid;

// @RestController
// @CrossOrigin(origins = "http://localhost:3000")
// @RequestMapping("/api")
// public class UserController {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private EmailService emailService;
//     private Map<String, String> otpStore = new HashMap<>();

//     // @PostMapping("/register")
//     // public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
//     //     // Save user to the database
//     //     userRepository.save(user);

//     //     // Send confirmation email
//     //     emailService.sendEmail(user.getEmail(), "Welcome!", "Thank you for registering!");

//     //     return ResponseEntity.ok("User registered successfully!");
//     // }
//     // @PostMapping("/verify-otp")
//     // public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
//     //     String storedOtp = otpStore.get(email);
//     //     if (storedOtp != null && storedOtp.equals(otp)) {
//     //         otpStore.remove(email); // Remove OTP after verification
//     //         return ResponseEntity.ok("OTP verified successfully!");
//     //     }
//     //     return ResponseEntity.badRequest().body("Invalid OTP.");
//     // }
//     @PostMapping("/register")
//     public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
//         userRepository.save(user);
//         String otp = emailService.sendOtp(user.getEmail());
//         otpStore.put(user.getEmail(), otp);
//         return ResponseEntity.ok(" An OTP has been sent to your email.");
//     }

//     // @PostMapping("/verify-otp")
//     // public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
//     //     String storedOtp = otpStore.get(email);
//     //     if (storedOtp != null && storedOtp.equals(otp)) {
//     //         otpStore.remove(email);
//     //         return ResponseEntity.ok("OTP verified successfully!");
//     //     }
//     //     return ResponseEntity.badRequest().body("Invalid OTP.");
//     // }
//     @PostMapping("/verify-otp")
// public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
//     String storedOtp = otpStore.get(email);
//     if (storedOtp != null && storedOtp.equals(otp)) {
//         otpStore.remove(email);
//         return ResponseEntity.ok("OTP verified successfully!");
//     }
//     return ResponseEntity.badRequest().body("Invalid OTP.");
// }

// }

package com.example.emailapp.controller;

import com.example.emailapp.model.User;
import com.example.emailapp.repository.UserRepository;
import com.example.emailapp.service.EmailService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;
    private Map<String, String> otpStore = new HashMap<>();

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        // Save user to the database
        userRepository.save(user);
        
        // Send OTP to user's email
        String otp = emailService.sendOtp(user.getEmail());
        otpStore.put(user.getEmail(), otp);
        
        return ResponseEntity.ok("An OTP has been sent to your email.");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        String storedOtp = otpStore.get(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStore.remove(email);
            return ResponseEntity.ok("OTP verified successfully!");
        }
        return ResponseEntity.badRequest().body("Invalid OTP.");
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String email) {
        boolean emailSent = emailService.sendVerificationEmail(email);
        if (emailSent) {
            return ResponseEntity.ok("Verification email sent.");
        } else {
            return ResponseEntity.badRequest().body("Failed to send email. Please check the address.");
        }
    }
}

