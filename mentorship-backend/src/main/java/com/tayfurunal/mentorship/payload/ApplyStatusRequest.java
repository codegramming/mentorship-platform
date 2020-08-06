package com.tayfurunal.mentorship.payload;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ApplyStatusRequest {
    @NotBlank
    @ApiModelProperty(required = true)
    private String status;
}

