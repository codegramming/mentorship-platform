package com.tayfurunal.mentorship.service.impl;

import com.tayfurunal.mentorship.domain.User;
import com.tayfurunal.mentorship.payload.ApiError;
import com.tayfurunal.mentorship.payload.ApiResponse;
import com.tayfurunal.mentorship.payload.AuthResponse;
import com.tayfurunal.mentorship.payload.LoginRequest;
import com.tayfurunal.mentorship.payload.LoginResponse;
import com.tayfurunal.mentorship.payload.SignUpRequest;
import com.tayfurunal.mentorship.repository.UserRepository;
import com.tayfurunal.mentorship.security.TokenProvider;
import com.tayfurunal.mentorship.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return getApiError("Username", "Username is already taken!");
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return getApiError("Email", "Email is already taken!");
        }

        User user = new User(signUpRequest.getEmail(), signUpRequest.getUsername(), signUpRequest.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        ApiResponse response = new ApiResponse(true, "User registered successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AuthResponse> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        LoginResponse loginResponse = new LoginResponse(user.get().getId(), user.get().getEmail(),
                user.get().getUsername(), user.get().getAuthorities());
        System.out.println(user.toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token, loginResponse));
    }

    private ResponseEntity<ApiError> getApiError(String title, String message) {
        ApiError apiError = new ApiError(false, 400, "Validation Error", "/api/auth/signup");
        Map<String, String> validationErrors = new HashMap<>();
        validationErrors.put(title, message);
        apiError.setValidationErrors(validationErrors);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
