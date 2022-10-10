package com.rent.app.controllers;

import com.rent.app.models.User;
import com.rent.app.payload.request.ResetRequest;

import com.rent.app.payload.response.MessageResponse;
import com.rent.app.repository.UserRepository;
import com.rent.app.security.services.ResetUserImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Random;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reset")
@Slf4j
public class ResetController {

    private final UserRepository userRepository;

    private final ResetUserImpl resetUser;

    public ResetController(UserRepository userRepository, ResetUserImpl resetUser) {
        this.userRepository = userRepository;
        this.resetUser = resetUser;
    }

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/sendOTP")
    public ResponseEntity<?> sendOTP(@Valid @RequestBody ResetRequest resetRequest) {

        if (!userRepository.existsByEmail(resetRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Please Enter valid registered Email"));
        }
        try {
            resetUser.sendResetOTP(resetRequest.getEmail());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Error: Error in sending mail. Please try again later"));
        }

        return ResponseEntity.ok(new MessageResponse("OTP sent successfully!"));
    }

    @PostMapping("/submitOTP")
    public ResponseEntity<?> submitOTP(@Valid @RequestBody ResetRequest resetRequest) {

        String error = "";

        if (!userRepository.existsByEmail(resetRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is not Registered"));
        }
        try {
            String storedOtp = resetUser.sendResetOTP(resetRequest.getEmail());
            if (null != storedOtp || resetRequest.getOtp() != null || storedOtp.equalsIgnoreCase(resetRequest.getOtp())) {
                resetUser.evictCache(resetRequest.getEmail());
                return ResponseEntity.ok(new MessageResponse("OTP verified successfully!"));
            }
        } catch (Exception e) {
            error = "Error: Error in verifying OTP. Please try again later";
        }

        return ResponseEntity.internalServerError().body(new MessageResponse("Error: Error in verifying OTP. Please try again later"));

    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetRequest resetRequest) {
        if (!userRepository.existsByEmail(resetRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is not Registered"));
        }
        Optional<User> user = userRepository.findByEmail(resetRequest.getEmail());
        if (user.isPresent()) {
            userRepository.updatePassword(encoder.encode(resetRequest.getPassword()), user.get().getId());
        }
        return ResponseEntity.ok(new MessageResponse("Password reset success"));
    }
}
