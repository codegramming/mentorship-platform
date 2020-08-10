package com.tayfurunal.mentorship.service;

import com.tayfurunal.mentorship.domain.Topic;
import com.tayfurunal.mentorship.payload.ApiResponse;

import org.springframework.http.ResponseEntity;

public interface TopicService {

    ResponseEntity<ApiResponse> createMainTopic(Topic main);

    ResponseEntity<Topic> getById(Long id);

    ResponseEntity<?> getAll();

    ResponseEntity<ApiResponse> addSubTopic(Long id, String sub);

    ResponseEntity<ApiResponse> removeSubTopic(Long id, String sub);
}
