package com.tayfurunal.mentorship.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mainTopic;

    private String subTopics;

    private String thoughts;

    @Enumerated(EnumType.STRING)
    private progressStatus status = progressStatus.IN_PROGRESS;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mentee> mentees = new ArrayList<>();

    public static enum progressStatus {
        IN_PROGRESS,
        ACCEPTED,
        NOT_ACCEPTED
    }
}