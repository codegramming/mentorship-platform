package com.tayfurunal.mentorship.repository.jpa;

import com.tayfurunal.mentorship.domain.Mentorship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorshipRepository extends JpaRepository<Mentorship, Long> {
}
