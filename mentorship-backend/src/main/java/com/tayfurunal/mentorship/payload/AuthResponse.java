package com.tayfurunal.mentorship.payload;

import lombok.Getter;

@Getter
public class AuthResponse {
    private final String accessToken;

    private final String tokenType = "Bearer";

    private final LoginResponse loginResponse;

    public AuthResponse(String accessToken, LoginResponse loginResponse) {
        this.accessToken = accessToken;
        this.loginResponse = loginResponse;
    }
}
