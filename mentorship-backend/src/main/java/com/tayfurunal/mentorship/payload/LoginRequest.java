package com.tayfurunal.mentorship.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NonNull
    @NotBlank
    @Size(min = 1, max = 128)
    @ApiModelProperty(required = true)
    private String username;

    @NonNull
    @NotBlank
    @Size(min = 1, max = 128)
    @ApiModelProperty(required = true)
    private String password;
}
