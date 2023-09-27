package com.app.trackmeuser.model.dto;

import com.app.trackmeuser.model.UserRoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {

    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
	@Pattern(regexp = "(\\d{3})-(\\d{3,4})-(\\d{4})")
    private String phoneNumber;

    private UserRoleType role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
