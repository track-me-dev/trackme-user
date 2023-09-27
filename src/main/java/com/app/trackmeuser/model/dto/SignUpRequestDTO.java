package com.app.trackmeuser.model.dto;

import com.app.trackmeuser.model.UserRoleType;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[~@#!$%^&+=])(?=\\S+$).{6,20}$", message = "비밀번호 규칙과 동일하지 않습니다.")
    private String password;

    @NotBlank
	@Pattern(regexp = "(\\d{3})-(\\d{3,4})-(\\d{4})")
    private String phoneNumber;

    private UserRoleType role;
}
