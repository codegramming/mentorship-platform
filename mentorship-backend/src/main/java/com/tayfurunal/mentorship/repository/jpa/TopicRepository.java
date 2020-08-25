package com.tayfurunal.mentorship.repository.jpa;

import com.tayfurunal.mentorship.domain.Topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Topic getById(Long id);

    Topic getByTitle(String title);

    Topic getBySubTitle(String sub);
}
