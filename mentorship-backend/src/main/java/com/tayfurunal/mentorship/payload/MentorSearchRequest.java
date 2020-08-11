package com.tayfurunal.mentorship.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MentorSearchRequest {
    @ApiModelProperty(required = true)
    private String main;
}
