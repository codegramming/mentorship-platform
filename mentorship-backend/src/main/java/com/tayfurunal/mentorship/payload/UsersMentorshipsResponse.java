package com.tayfurunal.mentorship.payload;

import com.tayfurunal.mentorship.domain.Mentorship;

import java.util.List;

import lombok.Data;

@Data
public class UsersMentorshipsResponse {
    List<Mentorship> mentors;

    List<Mentorship> mentees;
}
