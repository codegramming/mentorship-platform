package com.tayfurunal.mentorship.payload;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse {
    @NonNull
    private final boolean success;

    @NonNull
    private final String message;
}

