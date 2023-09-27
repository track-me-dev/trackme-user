package com.app.trackmeuser.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
