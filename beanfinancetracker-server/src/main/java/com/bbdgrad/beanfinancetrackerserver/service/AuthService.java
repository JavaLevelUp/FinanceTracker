package com.bbdgrad.beanfinancetrackerserver.service;

import com.bbdgrad.beanfinancetrackerserver.controller.auth.AuthRequest;
import com.bbdgrad.beanfinancetrackerserver.controller.auth.AuthResponse;
import com.bbdgrad.beanfinancetrackerserver.controller.auth.RegisterRequest;
import com.bbdgrad.beanfinancetrackerserver.model.User;
import com.bbdgrad.beanfinancetrackerserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public AuthResponse register(RegisterRequest request) {
        Optional<User> userExists = userRepository.findByEmail(request.getEmail());
        if(userExists.isPresent()) {
            System.out.println("User already exists");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .created_at(new Timestamp(System.currentTimeMillis()))
                .build();
        var savedUser = userRepository.save(user);

        return AuthResponse.builder()
                .accessToken("jwtToken")
                .refreshToken("refreshToken")
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        return AuthResponse.builder()
                .accessToken("jwtToken - auth")
                .refreshToken("refreshToken - auth")
                .build();
    }


}
