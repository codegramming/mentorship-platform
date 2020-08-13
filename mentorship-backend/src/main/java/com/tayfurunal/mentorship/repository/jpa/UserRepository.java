package com.tayfurunal.mentorship.repository.jpa;

import com.tayfurunal.mentorship.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User getByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);
}

