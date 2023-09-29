package com.app.trackmeuser.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("authorization")
public class AuthConstant {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    private String debugKey;
    private String adminUser;
    private String adminPassword;
}
