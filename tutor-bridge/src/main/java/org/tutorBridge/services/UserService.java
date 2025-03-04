package org.tutorBridge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tutorBridge.repositories.UserRepo;
import org.tutorBridge.entities.User;
import org.tutorBridge.validation.ValidationException;

import java.util.List;


@Service
public abstract class UserService<T extends User> {
    protected final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    protected UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    protected void registerUser(T user) {
        boolean userWithSameEmailExists = userRepo.findByEmail(user.getEmail()).isPresent();
        if (userWithSameEmailExists) {
            throw new ValidationException(List.of("User with the same email already exists"));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        saveUser(user);
    }

    protected abstract void saveUser(T user);
}
