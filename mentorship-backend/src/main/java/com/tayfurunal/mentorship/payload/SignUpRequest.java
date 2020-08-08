package com.tayfurunal.mentorship.payload;

import javax.validation.constraints.Email;
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
public class SignUpRequest {
    @NonNull
    @NotBlank
    @Size(min = 4, max = 16)
    @ApiModelProperty(required = true)
    private String username;

    @Email
    @NonNull
    @NotBlank
    @Size(min = 1, max = 129)
    @ApiModelProperty(required = true)
    private String email;

    @NonNull
    @NotBlank
    @Size(min = 4, max = 16)
    @ApiModelProperty(required = true)
    private String displayName;

    @NonNull
    @NotBlank
    @Size(min = 1, max = 128)
    @ApiModelProperty(required = true)
    private String password;
}
