package com.tayfurunal.mentorship.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private Long timestamp = new Date().getTime();

    private Boolean success;

    private int status;

    private String message;

    private String url;

    private Map<String, String> validationErrors;

    public ApiError(Boolean success, int status, String message, String url) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.url = url;
    }
}
