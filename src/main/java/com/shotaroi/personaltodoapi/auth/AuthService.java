package com.shotaroi.personaltodoapi.auth;

import com.shotaroi.personaltodoapi.auth.dto.LoginRequest;
import com.shotaroi.personaltodoapi.auth.dto.RegisterRequest;
import com.shotaroi.personaltodoapi.user.User;
import com.shotaroi.personaltodoapi.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthService(UserRepository userRepo, PasswordEncoder encoder, JwtService jwt) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public String register(RegisterRequest req) {
        if (userRepo.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .email(req.email())
                .passwordHash(encoder.encode(req.password()))
                .build();

        userRepo.save(user);
        return jwt.generateToken(user.getEmail());
    }

    public String login(LoginRequest req) {
        User user = userRepo.findByEmail(req.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!encoder.matches(req.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return jwt.generateToken(user.getEmail());
    }
}

