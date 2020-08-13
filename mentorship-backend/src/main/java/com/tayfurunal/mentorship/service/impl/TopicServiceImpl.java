package com.tayfurunal.mentorship.service.impl;

import com.tayfurunal.mentorship.domain.Topic;
import com.tayfurunal.mentorship.payload.ApiResponse;
import com.tayfurunal.mentorship.repository.jpa.TopicRepository;
import com.tayfurunal.mentorship.service.TopicService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;

    TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }


    @Override
    public ResponseEntity<ApiResponse> createMainTopic(Topic main) {
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
    public ResponseEntity<ApiResponse> addSubTopic(Long id, String subTitle) {
        Topic topic1 = topicRepository.getById(id);
        Set<String> sub = topic1.getSubTitle();
        sub.add(subTitle);
        topic1.setSubTitle(sub);
        topicRepository.save(topic1);

        ApiResponse response = new ApiResponse(true, "Sub Topic has been added");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> removeSubTopic(Long id, String subTitle) {
        Topic topic1 = topicRepository.getById(id);
        Set<String> sub = topic1.getSubTitle();
        sub.remove(subTitle);
        topic1.setSubTitle(sub);
        topicRepository.save(topic1);

        ApiResponse response = new ApiResponse(true, "Sub Topic has been added");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
