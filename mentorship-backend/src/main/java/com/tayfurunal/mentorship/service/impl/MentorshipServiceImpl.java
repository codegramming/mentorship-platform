package com.tayfurunal.mentorship.service.impl;

import com.tayfurunal.mentorship.domain.Mentee;
import com.tayfurunal.mentorship.domain.Mentor;
import com.tayfurunal.mentorship.domain.Mentorship;
import com.tayfurunal.mentorship.domain.Phase;
import com.tayfurunal.mentorship.domain.User;
import com.tayfurunal.mentorship.dto.MentorshipDto;
import com.tayfurunal.mentorship.payload.MentorshipDetailsResponse;
import com.tayfurunal.mentorship.repository.jpa.MenteeRepository;
import com.tayfurunal.mentorship.repository.jpa.MentorRepository;
import com.tayfurunal.mentorship.repository.jpa.MentorshipRepository;
import com.tayfurunal.mentorship.repository.jpa.PhaseRepository;
import com.tayfurunal.mentorship.repository.jpa.UserRepository;
import com.tayfurunal.mentorship.service.MentorshipService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MentorshipServiceImpl implements MentorshipService {

    private MentorRepository mentorRepository;
    private UserRepository userRepository;
    private MentorshipRepository mentorshipRepository;
    private MenteeRepository menteeRepository;
    private PhaseRepository phaseRepository;

    public MentorshipServiceImpl(MentorRepository mentorRepository, UserRepository userRepository,
                                 MentorshipRepository mentorshipRepository, MenteeRepository menteeRepository,
                                 PhaseRepository phaseRepository) {
        this.mentorRepository = mentorRepository;
        this.userRepository = userRepository;
        this.mentorshipRepository = mentorshipRepository;
        this.menteeRepository = menteeRepository;
        this.phaseRepository = phaseRepository;
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
        mentorship.setHasPhase(false);

        menteeRepository.save(mentee);
        mentorshipRepository.save(mentorship);
        return new ResponseEntity<>(mentorship, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getMentorshipDetailsById(Long id) {
        Mentorship mentorship = mentorshipRepository.getById(id);
        String mentorDisplayName = mentorship.getMentor().getUser().getDisplayName();
        String menteeDisplayName = mentorship.getMentee().getUser().getDisplayName();
        String status = mentorship.getStatus().getName();
        String startDate = mentorship.getStartDate().toString();
        Integer numberOfPhases = mentorship.getNumberOfPhases();
        Boolean hasPhase = mentorship.getHasPhase();
        String mentorThoughts = mentorship.getMentorThoughts();
        String menteeThoughts = mentorship.getMenteeThoughts();
        Integer currentPhase = mentorship.getCurrentPhase();
        List<Phase> phases = mentorship.getPhases();

        MentorshipDetailsResponse mentorshipDetailsResponse = new MentorshipDetailsResponse(mentorDisplayName,
                menteeDisplayName, status, startDate, numberOfPhases, hasPhase, mentorThoughts, menteeThoughts,
                currentPhase, phases);
        return new ResponseEntity<>(mentorshipDetailsResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addPhaseToMentorship(Long id, Phase phase) {
        Mentorship mentorship = mentorshipRepository.getById(id);


        phase.setMentorship(mentorship);
        phase.setStatus(Phase.phaseStatus.NOT_ACTIVE);
        phaseRepository.save(phase);

        mentorship.setNumberOfPhases(mentorship.getPhases().size());
        mentorship.setHasPhase(true);
        mentorshipRepository.save(mentorship);

        return new ResponseEntity<>(phase, HttpStatus.OK);
    }
}
