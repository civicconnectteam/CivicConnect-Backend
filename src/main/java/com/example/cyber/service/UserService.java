package com.example.cyber.service;

import com.example.cyber.model.User;
import com.example.cyber.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // Register user with duplicate email check
    public User register(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (user.getRole() == null || user.getRole().isEmpty()) user.setRole("USER");
        return userRepo.save(user);
    }

    public Optional<User> login(String email, String password) {
        return userRepo.findByEmailAndPassword(email, password);
    }

    public Optional<User> getProfile(Long id) {
        return userRepo.findById(id);
    }

    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public User saveUser(User user) {
        return userRepo.save(user);
    }
    
    public User getUserById(Long id) {
        return userRepo.findById(id)  // ✅ use the instance
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
