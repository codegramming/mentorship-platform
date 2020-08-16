package com.tayfurunal.mentorship.service;

import com.tayfurunal.mentorship.domain.Phase;
import com.tayfurunal.mentorship.dto.MentorshipDto;

import org.springframework.http.ResponseEntity;

public interface MentorshipService {

    ResponseEntity<?> createMentorship(MentorshipDto mentorshipDto, String username);

    ResponseEntity<?> getMentorshipDetailsById(Long id);

    ResponseEntity<?> addPhaseToMentorship(Long id, Phase phase);
}
