package com.tayfurunal.mentorship.service;

import com.tayfurunal.mentorship.payload.AuthResponse;
import com.tayfurunal.mentorship.payload.LoginRequest;
import com.tayfurunal.mentorship.payload.SignUpRequest;

import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> registerUser(SignUpRequest signUpRequest);

    ResponseEntity<AuthResponse> authenticateUser(LoginRequest loginRequest);
}
