package com.tayfurunal.mentorship.service;

import com.tayfurunal.mentorship.domain.Topic;
import com.tayfurunal.mentorship.payload.ApiResponse;

import org.springframework.http.ResponseEntity;

public interface TopicService {

    ResponseEntity<ApiResponse> createMainTopic(Topic main);

    ResponseEntity<Topic> getById(Long id);

    ResponseEntity<ApiResponse> addSubTopic(Long id, String sub);
}
