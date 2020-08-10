package com.tayfurunal.mentorship.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MentorDto {
    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long id;

    @ApiModelProperty(required = true)
    @NotBlank
    private String mainTopic;

    @ApiModelProperty(required = true)
    @NotBlank
    private String subTopics;

    @ApiModelProperty(required = true)
    @NotBlank
    private String thoughts;
}
