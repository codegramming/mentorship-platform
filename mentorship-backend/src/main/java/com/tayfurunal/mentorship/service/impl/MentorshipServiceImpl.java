package com.tayfurunal.mentorship.service.impl;

import com.tayfurunal.mentorship.domain.Mentee;
import com.tayfurunal.mentorship.domain.Mentor;
import com.tayfurunal.mentorship.domain.Mentorship;
import com.tayfurunal.mentorship.domain.Phase;
import com.tayfurunal.mentorship.domain.User;
import com.tayfurunal.mentorship.dto.MentorshipDto;
import com.tayfurunal.mentorship.exception.MentorshipNotFoundException;
import com.tayfurunal.mentorship.payload.PhaseRequest;
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
        mentorship.setStatus(Mentorship.status.NOT_STARTED.getName());
        mentorship.setHasPhase(false);

        menteeRepository.save(mentee);
        mentorshipRepository.save(mentorship);
        return new ResponseEntity<>(mentorship, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getMentorshipDetailsById(Long id, String username) {
        Mentorship mentorship = mentorshipRepository.getById(id);
        String mentorUsernme = mentorship.getMentor().getUser().getUsername();
        String menteeUsername = mentorship.getMentee().getUser().getUsername();

        if (mentorUsernme.equals(username) || menteeUsername.equals(username)) {
            return new ResponseEntity<>(mentorship, HttpStatus.OK);
        } else {
            throw new MentorshipNotFoundException("Mentorship not found in your account");
        }
    }

    @Override
    public ResponseEntity<?> addPhaseToMentorship(Long id, Phase phase) {
        Mentorship mentorship = mentorshipRepository.getById(id);


        phase.setMentorship(mentorship);
        phase.setStatus(Phase.phaseStatus.NOT_ACTIVE);
        phase.setPhaseId(mentorship.getPhases().size() + 1);
        phaseRepository.save(phase);

        mentorship.setNumberOfPhases(mentorship.getPhases().size());
        mentorship.setHasPhase(true);
        mentorshipRepository.save(mentorship);

        return new ResponseEntity<>(phase, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> startMentorship(Long id) {
        Mentorship mentorship = mentorshipRepository.getById(id);
        List<Phase> phases = mentorship.getPhases();
        phases.get(0).setStatus(Phase.phaseStatus.ACTIVE);

        mentorship.setStatus(Mentorship.status.CONTINUING.getName());
        mentorship.setCurrentPhase(1);
        mentorship.setPhases(phases);

        mentorshipRepository.save(mentorship);
        return new ResponseEntity<>(mentorship, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> completePhase(Long id, Integer phaseId, String username, PhaseRequest phaseRequest) {
        Phase phase = phaseRepository.getById(id);
        Mentorship mentorship = phase.getMentorship();
        UserDetails userDetails = detectUser(mentorship, username);

        if (userDetails.equals(UserDetails.MENTOR)) {
            phase.setAssessmentOfMentor(phaseRequest.getAssessments());
            phase.setStatus(Phase.phaseStatus.PENDING);
            mentorship.setCurrentPhase(mentorship.getCurrentPhase() + 1);
            mentorship.getPhases().forEach(phase1 -> {
                if (phase1.getPhaseId().equals(mentorship.getCurrentPhase())) {
                    phase1.setStatus(Phase.phaseStatus.ACTIVE);
                }
                if (phase1.getPhaseId().equals(mentorship.getNumberOfPhases())) {
                    mentorship.setStatus(Mentorship.status.COMPLETED.getName());
                }
            });
            //phase.setRatingOfMentor();
        } else if (userDetails.equals(UserDetails.MENTEE)) {
            phase.setAssessmentOfMentee(phaseRequest.getAssessments());
            phase.setStatus(Phase.phaseStatus.PENDING);
            mentorship.setCurrentPhase(mentorship.getCurrentPhase() + 1);
            mentorship.getPhases().forEach(phase1 -> {
                if (phase1.getPhaseId().equals(mentorship.getCurrentPhase())) {
                    phase1.setStatus(Phase.phaseStatus.ACTIVE);
                }
                if (phase1.getPhaseId().equals(mentorship.getNumberOfPhases())) {
                    mentorship.setStatus(Mentorship.status.COMPLETED.getName());
                }
            });
            //phase.setRatingOfMentor();
        }

        mentorshipRepository.save(mentorship);
        phaseRepository.save(phase);
        return new ResponseEntity<>(phase, HttpStatus.OK);
    }

    public UserDetails detectUser(Mentorship mentorship, String username) {
        String mentorUsernme = mentorship.getMentor().getUser().getUsername();
        String menteeUsername = mentorship.getMentee().getUser().getUsername();

        if (mentorUsernme.equals(username)) {
            return UserDetails.MENTOR;
        } else if (menteeUsername.equals(username)) {
            return UserDetails.MENTEE;
        } else {
            throw new MentorshipNotFoundException("Mentorship not found in your account");
        }
    }

    private enum UserDetails {
        MENTOR,
        MENTEE
    }
}
