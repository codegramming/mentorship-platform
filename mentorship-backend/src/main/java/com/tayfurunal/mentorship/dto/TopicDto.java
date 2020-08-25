package com.tayfurunal.mentorship.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TopicDto {
    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long id;

    @ApiModelProperty(required = true)
    private String title;

    @ApiModelProperty(required = false)
    private String subTitle;
}
