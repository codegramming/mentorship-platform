package com.tayfurunal.mentorship.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ScheduleEmailResponse {
    private boolean success;
    private String jobId;
    private String jobGroup;
    private String message;

    private Map<String, String> validationErrors;

    public ScheduleEmailResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ScheduleEmailResponse(boolean success, String jobId, String jobGroup, String message) {
        this.success = success;
        this.jobId = jobId;
        this.jobGroup = jobGroup;
        this.message = message;
    }
}

