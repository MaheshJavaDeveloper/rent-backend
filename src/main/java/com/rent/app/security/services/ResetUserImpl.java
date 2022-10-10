package com.rent.app.security.services;

import com.rent.app.Handler.MailHandler;
import com.rent.app.models.User;
import com.rent.app.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ResetUserImpl {
    private final MailHandler mailHandler;
    private final UserRepository userRepository;

    public ResetUserImpl(MailHandler mailHandler, UserRepository userRepository) {
        this.mailHandler = mailHandler;
        this.userRepository = userRepository;
    }

    private final String SUBJECT_RESET = "Reset Your Rental Homes Account Password";

    @Cacheable(value = "resetMap", key = "#email")
    public String sendResetOTP(String email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            Random rnd = new Random();
            int number = rnd.nextInt(999999);
            String otp = String.format("%06d", number);
            mailHandler.sendResetOTP(email, user.get().getUsername(), otp, SUBJECT_RESET);
            return otp;
        }
        return "";
    }

    @CacheEvict(value = "resetMap", key = "#email")
    public void evictCache(String email) {
    }
}
