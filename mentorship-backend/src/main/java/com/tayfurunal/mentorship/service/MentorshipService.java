package com.tayfurunal.mentorship.service;

import com.tayfurunal.mentorship.dto.MentorshipDto;

import org.springframework.http.ResponseEntity;

public interface MentorshipService {

    ResponseEntity<?> createMentorship(MentorshipDto mentorshipDto, String username);

    ResponseEntity<?> getMentorshipDetailsById(Long id);
}
