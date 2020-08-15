package com.tayfurunal.mentorship.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MentorshipDto {
    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long id;

    @ApiModelProperty(required = true)
    private Long mentorId;

    @ApiModelProperty(required = false)
    private String mentorThoughts;

    @ApiModelProperty(required = false)
    private String menteeThoughts;
}
