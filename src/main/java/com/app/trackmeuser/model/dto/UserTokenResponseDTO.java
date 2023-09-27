package com.app.trackmeuser.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenResponseDTO {

    private String accessToken;
    private String refreshToken;

}
