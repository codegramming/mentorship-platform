package com.tayfurunal.mentorship.payload;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse {
    @NotBlank
    private final boolean success;

    @NotBlank
    private final String message;
}

