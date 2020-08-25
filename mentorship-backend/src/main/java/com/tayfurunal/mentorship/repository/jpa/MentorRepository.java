package com.tayfurunal.mentorship.repository.jpa;

import com.tayfurunal.mentorship.domain.Mentor;
import com.tayfurunal.mentorship.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {

    List<Mentor> findAllByStatusEquals(Mentor.progressStatus status);

    Mentor getById(Long id);

    List<Mentor> findAllByUser(User user);

    List<Mentor> findAllByStatusEqualsAndMainTopicEquals(Mentor.progressStatus status, String mainTopic);

    Mentor findByIdAndStatusEquals(Long id, Mentor.progressStatus status);

    List<Mentor> findAllByMainTopic(String main);

    List<Mentor> findAllBySubTopics(String sub);
}
