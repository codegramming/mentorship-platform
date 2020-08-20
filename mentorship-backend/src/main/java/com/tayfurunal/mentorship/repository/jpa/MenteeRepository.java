package com.tayfurunal.mentorship.repository.jpa;

import com.tayfurunal.mentorship.domain.Mentee;
import com.tayfurunal.mentorship.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenteeRepository extends JpaRepository<Mentee, Long> {

    List<Mentee> findAllByUser(User user);
}
