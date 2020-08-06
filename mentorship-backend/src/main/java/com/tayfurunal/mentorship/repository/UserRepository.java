package com.tayfurunal.mentorship.repository;

import com.tayfurunal.mentorship.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User getByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);
}

