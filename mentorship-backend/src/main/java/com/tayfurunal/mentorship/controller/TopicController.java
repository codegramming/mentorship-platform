package com.tayfurunal.mentorship.controller;

import com.tayfurunal.mentorship.domain.Topic;
import com.tayfurunal.mentorship.dto.TopicDto;
import com.tayfurunal.mentorship.mapper.TopicMapper;
import com.tayfurunal.mentorship.service.TopicService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/topics")
@Api
public class TopicController {

    private TopicService topicService;

    TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping()
    ResponseEntity<?> createTopic(@Valid @RequestBody TopicDto topicDto) {
        System.out.println(topicDto);
        Topic topic = TopicMapper.INSTANCE.toEntity(topicDto);
        System.out.println(topic);
        return topicService.createMainTopic(topic);
    }
}
