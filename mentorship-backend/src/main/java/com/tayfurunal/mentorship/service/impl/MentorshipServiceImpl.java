package com.tayfurunal.mentorship.service.impl;

import com.tayfurunal.mentorship.domain.Mentee;
import com.tayfurunal.mentorship.domain.Mentor;
import com.tayfurunal.mentorship.domain.Mentorship;
import com.tayfurunal.mentorship.domain.User;
import com.tayfurunal.mentorship.dto.MentorshipDto;
import com.tayfurunal.mentorship.payload.MentorshipDetailsResponse;
import com.tayfurunal.mentorship.repository.jpa.MenteeRepository;
import com.tayfurunal.mentorship.repository.jpa.MentorRepository;
import com.tayfurunal.mentorship.repository.jpa.MentorshipRepository;
import com.tayfurunal.mentorship.repository.jpa.UserRepository;
import com.tayfurunal.mentorship.service.MentorshipService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MentorshipServiceImpl implements MentorshipService {

    private MentorRepository mentorRepository;
    private UserRepository userRepository;
    private MentorshipRepository mentorshipRepository;
    private MenteeRepository menteeRepository;

    public MentorshipServiceImpl(MentorRepository mentorRepository, UserRepository userRepository,
                                 MentorshipRepository mentorshipRepository, MenteeRepository menteeRepository) {
        this.mentorRepository = mentorRepository;
        this.userRepository = userRepository;
        this.mentorshipRepository = mentorshipRepository;
        this.menteeRepository = menteeRepository;
    }

    public ResponseEntity<?> createMentorship(MentorshipDto mentorshipDto, String username) {
        User user = userRepository.getByUsername(username);
        Mentor mentor = mentorRepository.getById(mentorshipDto.getMentorId());
        Mentee mentee = new Mentee();
        mentee.setMentor(mentor);
        mentee.setUser(user);
        user.setMentees(Collections.singleton(mentee));

        Mentorship mentorship = new Mentorship();
        mentorship.setMentee(mentee);
        mentorship.setMentor(mentor);
        mentorship.setCurrentPhase(0);
        mentorship.setStatus(Mentorship.status.NOT_STARTED);

        menteeRepository.save(mentee);
        mentorshipRepository.save(mentorship);
        return new ResponseEntity<>(mentorship, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getMentorshipDetailsById(Long id) {
        Mentorship mentorship = mentorshipRepository.getById(id);
        String mentorDisplayName = mentorship.getMentor().getUser().getDisplayName();
        String menteeDisplayName = mentorship.getMentee().getUser().getDisplayName();
        String status = mentorship.getStatus().toString();
        String startDate = mentorship.getStartDate().toString();
        MentorshipDetailsResponse mentorshipDetailsResponse = new MentorshipDetailsResponse(mentorDisplayName,
                menteeDisplayName, status, startDate);
        return new ResponseEntity<>(mentorshipDetailsResponse, HttpStatus.OK);
    }
}
