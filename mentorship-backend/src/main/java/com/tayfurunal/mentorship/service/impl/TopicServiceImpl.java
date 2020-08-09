package com.tayfurunal.mentorship.service.impl;

import com.tayfurunal.mentorship.domain.Topic;
import com.tayfurunal.mentorship.payload.ApiResponse;
import com.tayfurunal.mentorship.repository.TopicRepository;
import com.tayfurunal.mentorship.service.TopicService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;

    TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }


    @Override
    public ResponseEntity<ApiResponse> createMainTopic(Topic main) {
        System.out.println(main);
        Topic topic = new Topic();
        topic.setTitle(main.getTitle());
        topicRepository.save(topic);

        ApiResponse response = new ApiResponse(true, "Topic has been created");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Topic> getById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> addSubTopic(Long id, String sub) {
        return null;
    }
}
