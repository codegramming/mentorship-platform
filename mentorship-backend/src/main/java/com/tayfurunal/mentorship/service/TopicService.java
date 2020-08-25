package com.tayfurunal.mentorship.service;

import com.tayfurunal.mentorship.domain.Topic;
import com.tayfurunal.mentorship.dto.TopicDto;

import org.springframework.http.ResponseEntity;

public interface TopicService {

    ResponseEntity<?> createMainTopic(TopicDto main);

    ResponseEntity<Topic> getById(Long id);

    ResponseEntity<?> getAll();

    ResponseEntity<?> addSubTopic(String title, String sub);

    ResponseEntity<?> removeMainTopic(String main);

    ResponseEntity<?> removeSubTopic(String sub);
}
