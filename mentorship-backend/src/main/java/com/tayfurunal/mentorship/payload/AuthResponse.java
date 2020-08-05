package com.tayfurunal.mentorship.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthResponse {
    private final String accessToken;

    private final String tokenType = "Bearer";
}
