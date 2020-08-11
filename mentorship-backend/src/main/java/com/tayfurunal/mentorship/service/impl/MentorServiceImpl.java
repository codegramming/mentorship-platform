package com.tayfurunal.mentorship.service.impl;

import com.tayfurunal.mentorship.domain.Mentor;
import com.tayfurunal.mentorship.domain.User;
import com.tayfurunal.mentorship.payload.ApiError;
import com.tayfurunal.mentorship.payload.ApiResponse;
import com.tayfurunal.mentorship.payload.ApplyStatusRequest;
import com.tayfurunal.mentorship.repository.MentorRepository;
import com.tayfurunal.mentorship.repository.UserRepository;
import com.tayfurunal.mentorship.service.MentorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentorServiceImpl implements MentorService {

    private UserRepository userRepository;
    private MentorRepository mentorRepository;

    public MentorServiceImpl(UserRepository userRepository, MentorRepository mentorRepository) {
        this.userRepository = userRepository;
        this.mentorRepository = mentorRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> applyMentorship(Mentor mentor, String username) {
        User user = userRepository.getByUsername(username);
        user.getMentors().add(mentor);
        mentor.setUser(user);
        mentorRepository.save(mentor);

        ApiResponse response = new ApiResponse(true, "Mentorship application is successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<?> changeApplyStatus(ApplyStatusRequest status, Long id) {
        Mentor mentor = mentorRepository.findByIdAndStatusEquals(id, Mentor.progressStatus.IN_PROGRESS);
        Mentor.progressStatus newStatus = null;
        if (status.getStatus().equals("ACCEPTED")) {
            newStatus = Mentor.progressStatus.ACCEPTED;
        } else if (status.getStatus().equals("NOT_ACCEPTED")) {
            newStatus = Mentor.progressStatus.NOT_ACCEPTED;
        } else {
            ApiError apiError = new ApiError(false, HttpStatus.BAD_REQUEST.value(), "Status is not valid", "/api/mentors" +
                    "/changeApplyStatus");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        mentor.setStatus(newStatus);
        mentorRepository.save(mentor);
        return new ResponseEntity<Mentor>(mentor, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllByInProgress() {
        List<Mentor> mentors = mentorRepository.findAllByStatusEquals(Mentor.progressStatus.IN_PROGRESS);
        return new ResponseEntity<>(mentors, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Mentor> getByIdAndInProgress(Long id) {
        Mentor mentor = mentorRepository.findByIdAndStatusEquals(id, Mentor.progressStatus.IN_PROGRESS);
        return new ResponseEntity<Mentor>(mentor, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllByAccepted() {
        List<Mentor> mentors = mentorRepository.findAllByStatusEquals(Mentor.progressStatus.ACCEPTED);
        return new ResponseEntity<>(mentors, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllByAcceptedWithMain(String main) {
        List<Mentor> mentors =
                mentorRepository.findAllByStatusEqualsAndMainTopicEquals(Mentor.progressStatus.ACCEPTED, main);
        return new ResponseEntity<>(mentors, HttpStatus.OK);
    }
}
