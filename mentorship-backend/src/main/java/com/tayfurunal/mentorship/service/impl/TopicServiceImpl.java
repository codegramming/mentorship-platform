package com.tayfurunal.mentorship.service.impl;

import com.tayfurunal.mentorship.domain.Mentor;
import com.tayfurunal.mentorship.domain.Topic;
import com.tayfurunal.mentorship.dto.TopicDto;
import com.tayfurunal.mentorship.payload.ApiError;
import com.tayfurunal.mentorship.payload.ApiResponse;
import com.tayfurunal.mentorship.repository.jpa.MentorRepository;
import com.tayfurunal.mentorship.repository.jpa.TopicRepository;
import com.tayfurunal.mentorship.service.TopicService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;
    private MentorRepository mentorRepository;

    TopicServiceImpl(TopicRepository topicRepository, MentorRepository mentorRepository) {
        this.topicRepository = topicRepository;
        this.mentorRepository = mentorRepository;
    }


    @Override
    public ResponseEntity<?> createMainTopic(TopicDto main) {
        if (main.getTitle().equals("")) {
            ApiError apiError = new ApiError(false, 400, "Validation Error", "/api/topics");
            Map<String, String> validationErrors = new HashMap<>();
            validationErrors.put("mainTopic", "Main Topic cannot be blank");
            apiError.setValidationErrors(validationErrors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        Topic existTopic = topicRepository.getByTitle(main.getTitle());
        if (existTopic != null) {
            ApiError apiError = new ApiError(false, 400, "Validation Error", "/api/topics");
            Map<String, String> validationErrors = new HashMap<>();
            validationErrors.put("mainTopic", "Main Topic already exists");
            apiError.setValidationErrors(validationErrors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        Topic topic = new Topic();
        topic.setTitle(main.getTitle());
        topicRepository.save(topic);

        ApiResponse response = new ApiResponse(true, "Topic has been created");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Topic> getById(Long id) {
        Topic topic = topicRepository.getById(id);
        return new ResponseEntity<Topic>(topic, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Topic> topics = topicRepository.findAll();
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addSubTopic(String title, String subTitle) {
        Topic topic = topicRepository.getByTitle(title);
        Topic subTopic = topicRepository.getBySubTitle(subTitle);

        if (subTitle.equals("")) {
            ApiError apiError = new ApiError(false, 400, "Validation Error", "/api/topics");
            Map<String, String> validationErrors = new HashMap<>();
            validationErrors.put("subTopic", "Sub Topic cannot be blank");
            apiError.setValidationErrors(validationErrors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        if (subTopic != null) {
            ApiError apiError = new ApiError(false, 400, "Validation Error", "/api/topics");
            Map<String, String> validationErrors = new HashMap<>();
            validationErrors.put("subTopic", "Sub Topic already exists");
            apiError.setValidationErrors(validationErrors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }


        Set<String> sub = topic.getSubTitle();
        sub.add(subTitle);
        topic.setSubTitle(sub);
        topicRepository.save(topic);

        ApiResponse response = new ApiResponse(true, "Sub Topic has been added");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeMainTopic(String main) {
        List<Mentor> mentors = mentorRepository.findAllByMainTopic(main);
        if (mentors.isEmpty()) {
            Topic topic = topicRepository.getByTitle(main);
            topicRepository.delete(topic);
        } else {
            ApiError apiError = new ApiError(false, 400, "Validation Error", "/api/topics");
            Map<String, String> validationErrors = new HashMap<>();
            validationErrors.put("removeMainTopic", "Main Topic has users");
            apiError.setValidationErrors(validationErrors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        ApiResponse response = new ApiResponse(true, "Main Topic has been removed");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeSubTopic(String subTitle) {
        List<Mentor> mentors = mentorRepository.findAllBySubTopics(subTitle);
        if (mentors.isEmpty()) {
            Topic topic = topicRepository.getBySubTitle(subTitle);
            Set<String> sub = topic.getSubTitle();
            sub.remove(subTitle);
            topic.setSubTitle(sub);
            topicRepository.save(topic);
        } else {
            ApiError apiError = new ApiError(false, 400, "Validation Error", "/api/topics");
            Map<String, String> validationErrors = new HashMap<>();
            validationErrors.put("removeMainTopic", "Sub Topic has users");
            apiError.setValidationErrors(validationErrors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }


        ApiResponse response = new ApiResponse(true, "Sub Topic has been removed");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
