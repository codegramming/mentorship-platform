package com.tayfurunal.mentorship.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MentorshipNotFoundException extends RuntimeException {
    public MentorshipNotFoundException(String message) {
        super(message);
    }
}
