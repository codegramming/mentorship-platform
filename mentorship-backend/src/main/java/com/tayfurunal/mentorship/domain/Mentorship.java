package com.tayfurunal.mentorship.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Mentorship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mentorThoughts;

    private String menteeThoughts;

    @OneToOne
    Mentor mentor;

    @OneToOne
    Mentee mentee;

    Date startDate = new Date();

    @OneToMany(mappedBy = "mentorship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phase> phases = new ArrayList<>();

    private Integer numberOfPhases;

    private static Integer currentPhase = 0;
}
