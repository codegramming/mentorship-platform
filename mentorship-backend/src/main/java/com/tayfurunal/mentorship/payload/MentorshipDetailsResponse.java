package com.tayfurunal.mentorship.payload;

import com.tayfurunal.mentorship.domain.Phase;

import java.util.List;

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

    private Integer numberOfPhases;

    private Boolean hasPhase;

    private String mentorThoughts;

    private String menteeThoughts;

    private Integer currentPhase;

    private List<Phase> phases;

    public MentorshipDetailsResponse(String mentorDisplayName, String menteeDisplayName, String status,
                                     String startDate, Integer numberOfPhases, Boolean hasPhase,
                                     String mentorThoughts, String menteeThoughts, Integer currentPhase,
                                     List<Phase> phases) {
        this.mentorDisplayName = mentorDisplayName;
        this.menteeDisplayName = menteeDisplayName;
        this.status = status;
        this.startDate = startDate;
        this.numberOfPhases = numberOfPhases;
        this.hasPhase = hasPhase;
        this.mentorThoughts = mentorThoughts;
        this.menteeThoughts = menteeThoughts;
        this.currentPhase = currentPhase;
        this.phases = phases;
    }
}
