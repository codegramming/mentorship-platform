package com.tayfurunal.mentorship.repository;

import com.tayfurunal.mentorship.domain.Mentee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeRepository extends JpaRepository<Mentee, Long> {
}
