package com.tayfurunal.mentorship.repository.jpa;

import com.tayfurunal.mentorship.domain.Mentee;
import com.tayfurunal.mentorship.domain.Mentor;
import com.tayfurunal.mentorship.domain.Mentorship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorshipRepository extends JpaRepository<Mentorship, Long> {

    Mentorship getById(Long id);

    List<Mentorship> findAllByMentor(Mentor mentor);

    List<Mentorship> findAllByMentee(Mentee mentee);
}
