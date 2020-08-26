package com.tayfurunal.mentorship.domain;

import org.hibernate.annotations.OrderBy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    private Integer numberOfPhases;

    private Integer currentPhase;

    private Boolean hasPhase = false;

    @OneToOne
    Mentor mentor;

    @OneToOne
    Mentee mentee;

    Date nowDate = new Date();
    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    String startDate = fmt.format(nowDate);

    @OneToMany(mappedBy = "mentorship", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy(clause = "phaseId")
    private List<Phase> phases = new ArrayList<>();

    private String status;

    public static enum status {
        NOT_STARTED("Not Started"),
        COMPLETED("Completed"),
        CONTINUING("Continuing");

        private String name;

        status(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

    }
}
