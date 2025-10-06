package com.upc.g1tf.security.services;

import com.upc.g1tf.security.entities.User;
import com.upc.g1tf.security.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean checkOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public boolean validateNewPassword(String newPassword) {
        // Reglas: mínimo 8 caracteres, al menos 1 mayúscula, 1 número y 1 símbolo
        return newPassword.matches("^[A-Za-z0-9]{8,}$");
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}

