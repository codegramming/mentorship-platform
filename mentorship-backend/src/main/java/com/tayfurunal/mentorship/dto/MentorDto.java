package com.tayfurunal.mentorship.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MentorDto {
    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long id;

    @ApiModelProperty(required = true)
    private String mainTopic;

    @ApiModelProperty(required = true)
    private String subTopics;

    @ApiModelProperty(required = true)
    private String thoughts;
}
