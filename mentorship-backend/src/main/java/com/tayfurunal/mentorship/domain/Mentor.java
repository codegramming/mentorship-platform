package com.tayfurunal.mentorship.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
@Document(indexName = "mentor")
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text)
    private String mainTopic;

    @Field(type = FieldType.Text)
    private String subTopics;

    @Field(type = FieldType.Text)
    private String thoughts;

    @Enumerated(EnumType.STRING)
    private progressStatus status = progressStatus.IN_PROGRESS;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Mentee> mentees = new ArrayList<>();

    public static enum progressStatus {
        IN_PROGRESS,
        ACCEPTED,
        NOT_ACCEPTED
    }
}