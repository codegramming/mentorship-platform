package com.tayfurunal.mentorship.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String endDate;

    private String assessmentOfMentor;

    private Integer ratingOfMentor;

    private String assessmentOfMentee;

    private String ratingOfMentee;

    @ManyToOne
    @JsonIgnore
    private Mentorship mentorship;

    @Enumerated(EnumType.STRING)
    private phaseStatus status;

    public static enum phaseStatus {
        NOT_ACTIVE,
        ACTIVE,
        COMPLETED
    }
}
