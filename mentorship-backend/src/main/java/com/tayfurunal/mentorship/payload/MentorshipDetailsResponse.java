package com.tayfurunal.mentorship.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MentorshipDetailsResponse {
    private String mentorDisplayName;

    private String menteeDisplayName;

    private String status;

    private String startDate;

    public MentorshipDetailsResponse(String mentorDisplayName, String menteeDisplayName, String status, String startDate) {
        this.mentorDisplayName = mentorDisplayName;
        this.menteeDisplayName = menteeDisplayName;
        this.status = status;
        this.startDate = startDate;
    }
}
