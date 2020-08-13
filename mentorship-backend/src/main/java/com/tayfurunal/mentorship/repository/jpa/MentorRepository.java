package com.tayfurunal.mentorship.repository;

import com.tayfurunal.mentorship.domain.Mentor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorRepository extends JpaRepository<Mentor, Long> {

    List<Mentor> findAllByStatusEquals(Mentor.progressStatus status);

    List<Mentor> findAllByStatusEqualsAndMainTopicEquals(Mentor.progressStatus status, String mainTopic);

    List<Mentor> findAllByStatusEqualsAndSubTopicsContaining(Mentor.progressStatus status, String subTopics);

    Mentor findByIdAndStatusEquals(Long id, Mentor.progressStatus status);
}