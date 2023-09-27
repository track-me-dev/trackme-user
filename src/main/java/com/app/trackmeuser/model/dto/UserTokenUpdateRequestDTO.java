package com.app.trackmeuser.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserTokenUpdateRequestDTO {

    @NotBlank
    private String refreshToken;
}
