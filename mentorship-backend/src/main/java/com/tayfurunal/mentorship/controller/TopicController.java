package com.tayfurunal.mentorship.controller;

import com.tayfurunal.mentorship.domain.Topic;
import com.tayfurunal.mentorship.dto.TopicDto;
import com.tayfurunal.mentorship.mapper.TopicMapper;
import com.tayfurunal.mentorship.service.TopicService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        Topic topic = TopicMapper.INSTANCE.toEntity(topicDto);
        return topicService.createMainTopic(topic);
    }

    @PostMapping("/{id}")
    ResponseEntity<?> addSubTopic(@PathVariable(value = "id", required = true) Long id, @Valid @RequestBody TopicDto topicDto) {
        return topicService.addSubTopic(id, topicDto.getSubTitle());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> removeSubTopic(@PathVariable(value = "id", required = true) Long id, @Valid @RequestBody TopicDto topicDto) {
        return topicService.removeSubTopic(id, topicDto.getSubTitle());
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getTopicById(@PathVariable(value = "id", required = true) Long id) {
        return topicService.getById(id);
    }

    @GetMapping
    ResponseEntity<?> getAllTopic() {
        return topicService.getAll();
    }
}
