package com.tayfurunal.mentorship.repository.jpa;

import com.tayfurunal.mentorship.domain.Mentee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenteeRepository extends JpaRepository<Mentee, Long> {
}
