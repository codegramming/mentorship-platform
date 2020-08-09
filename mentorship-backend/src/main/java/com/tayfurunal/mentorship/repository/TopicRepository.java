package com.tayfurunal.mentorship.repository;

import com.tayfurunal.mentorship.domain.Topic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Topic getById(Long id);
}
