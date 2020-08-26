package com.tayfurunal.mentorship.service.impl;

import com.tayfurunal.mentorship.domain.Mentor;
import com.tayfurunal.mentorship.domain.User;
import com.tayfurunal.mentorship.payload.ApiError;
import com.tayfurunal.mentorship.payload.ApiResponse;
import com.tayfurunal.mentorship.payload.ApplyStatusRequest;
import com.tayfurunal.mentorship.repository.elasticsearch.MentorSearchRepository;
import com.tayfurunal.mentorship.repository.jpa.MentorRepository;
import com.tayfurunal.mentorship.repository.jpa.UserRepository;
import com.tayfurunal.mentorship.service.MentorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

@Service
public class MentorServiceImpl implements MentorService {

    private UserRepository userRepository;
    private MentorRepository mentorRepository;
    private MentorSearchRepository mentorSearchRepository;

    public MentorServiceImpl(UserRepository userRepository, MentorRepository mentorRepository,
                             MentorSearchRepository mentorSearchRepository) {
        this.userRepository = userRepository;
        this.mentorRepository = mentorRepository;
        this.mentorSearchRepository = mentorSearchRepository;
    }

    @PostConstruct
    private void init() {
        ApplyStatusRequest applyStatusRequest = new ApplyStatusRequest();
        applyStatusRequest.setStatus("ACCEPTED");

        changeApplyStatus(applyStatusRequest, 300L);
        changeApplyStatus(applyStatusRequest, 301L);
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
        saveElasticRepository(mentor);
        return new ResponseEntity<Mentor>(mentor, HttpStatus.OK);
    }

    private void saveElasticRepository(Mentor mentor) {
        User user = new User();
        user.setId(mentor.getUser().getId());
        user.setEmail(mentor.getUser().getEmail());
        user.setUsername(mentor.getUser().getUsername());
        user.setDisplayName(mentor.getUser().getDisplayName());
        mentor.setUser(user);

        mentorSearchRepository.save(mentor);
    }

    @Override
    public ResponseEntity<?> getAllByInProgress() {
        List<Mentor> mentors = mentorRepository.findAllByStatusEquals(Mentor.progressStatus.IN_PROGRESS);
        return new ResponseEntity<>(mentors, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Mentor> getByIdAndProgressStatus(Long id, Mentor.progressStatus progressStatus) {
        Mentor mentor = mentorRepository.findByIdAndStatusEquals(id, progressStatus);
        return new ResponseEntity<Mentor>(mentor, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllByAccepted() {
        List<Mentor> mentors = mentorRepository.findAllByStatusEquals(Mentor.progressStatus.ACCEPTED);
        return new ResponseEntity<>(mentors, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllByAcceptedWithSearch(String main, String subs, String thoughts) {
        List<Mentor> mentors;
        if (main != null) {
            mentors = mentorRepository.findAllByStatusEqualsAndMainTopicEquals(Mentor.progressStatus.ACCEPTED, main);
        } else {
            List<Mentor> mentorList = mentorRepository.findAllByStatusEquals(Mentor.progressStatus.ACCEPTED);
            List<Mentor> searchList = mentorSearchRepository.findAll();

            searchList.forEach(mentor -> {
                AtomicBoolean isExist = new AtomicBoolean(false);
                mentorList.forEach(exitMentor -> {
                            if (mentor.getId().equals(exitMentor.getId())) {
                                isExist.set(true);
                            }
                        }
                );
                if (!isExist.get()) {
                    System.out.println("removed");
                    System.out.println(mentor);
                    mentorSearchRepository.delete(mentor);
                }
            });

            mentors = subs != null ? mentorSearchRepository.findBySubTopics(subs) :
                    mentorSearchRepository.findByThoughts(thoughts);
        }
        return new ResponseEntity<>(mentors, HttpStatus.OK);
    }
}
