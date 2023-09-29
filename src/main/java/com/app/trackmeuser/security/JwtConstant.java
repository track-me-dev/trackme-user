package com.app.trackmeuser.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("jwt")
public class JwtConstant {

    private String secretKey;
    private String accessTokenExpired;
    private String refreshTokenExpired;
}
