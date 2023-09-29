package com.app.trackmeuser.service;

import com.app.trackmeuser.model.User;
import com.app.trackmeuser.security.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenService {

    private final JwtConstant jwtConstant;
    // User 에 대한 변경사항이 있을경우 해당 코드가 변경되고 이를 기반으로 기존 유저의 수정이 발생했을때 토큰을 무효화한다.
    private final Map<String, Integer> USER_SECRET_KEY = new HashMap<>();
    private final UserService userService;

    /**
     *
     * @param username
     * @param updateTime
     */
    private void setUserCode(String username, LocalDateTime updateTime) {
        USER_SECRET_KEY.put(username, updateTime.hashCode());
    }

    public int getUserCode(String username) {
        return USER_SECRET_KEY.get(username);
    }

    public String genAccessToken(String username) {
        User user = userService.getUser(username);
        setUserCode(username, user.getUpdatedAt());
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .createAuthorityList(user.getRole().getValue());

        return Jwts
                .builder()
                .setSubject(username)
                .setIssuer("TRACKME_ADMIN")
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .claim("code", USER_SECRET_KEY.get(username))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConstant.getAccessTokenExpired()))
                .signWith(SignatureAlgorithm.HS512, jwtConstant.getSecretKey().getBytes()).compact();
    }

    public String genRefreshToken(String username) {
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuer("TRACKME_ADMIN")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConstant.getRefreshTokenExpired()))
                .signWith(SignatureAlgorithm.HS512, jwtConstant.getSecretKey().getBytes()).compact();
    }

    public String genAccessTokenByRefreshToken(String refreshToken) {
        Claims claims = validateToken(refreshToken);
        String username = claims.getSubject();
        return genAccessToken(username);
    }

    public Claims validateToken(String jwtToken) {
        return Jwts.parser().setSigningKey(jwtConstant.getSecretKey().getBytes()).parseClaimsJws(jwtToken).getBody();
    }
}
