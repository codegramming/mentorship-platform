package com.tayfurunal.mentorship.service;

import com.tayfurunal.mentorship.domain.Phase;
import com.tayfurunal.mentorship.dto.MentorshipDto;
import com.tayfurunal.mentorship.payload.PhaseRequest;

import org.springframework.http.ResponseEntity;

public interface MentorshipService {

    ResponseEntity<?> createMentorship(MentorshipDto mentorshipDto, String username);

    ResponseEntity<?> getMentorshipDetailsById(Long id, String username);

    ResponseEntity<?> addPhaseToMentorship(Long id, Phase phase);

    ResponseEntity<?> startMentorship(Long id);

    ResponseEntity<?> completePhase(Long id, String username, PhaseRequest phaseRequest);

    ResponseEntity<?> getMentorByUser(String username);

    ResponseEntity<?> getMenteeByUser(String username);
}
