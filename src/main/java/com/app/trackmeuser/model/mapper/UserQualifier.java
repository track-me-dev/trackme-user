package com.app.trackmeuser.model.mapper;

import com.app.trackmeuser.model.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserQualifier {

    private final PasswordEncoder passwordEncoder;

    @Named("EncodePassword")
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Named("UserToUsername")
    public String userToUsername(final User user) {
        return user.getUsername();
    }

}
