package com.app.trackmeuser.service;

import com.app.trackmeuser.model.User;
import com.app.trackmeuser.model.UserRoleType;
import com.app.trackmeuser.model.dto.UserLoginRequestDTO;
import com.app.trackmeuser.repository.UserRepository;
import com.app.trackmeuser.security.AuthConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final static Set<UserRoleType> USER_ROLE_FILTER_SET = new HashSet<>(List.of(UserRoleType.ROLE_CUSTOMER));

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User getUser(String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User Not Exist"));
    }

    @Transactional(readOnly = true)
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User signIn(UserLoginRequestDTO loginDTO) {
        User user = getUser(loginDTO.getUsername());
        // 패스워드 검증
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password Not Match");
        }
        return user;
    }

    public User signUp(User input) {
        try {
            return createUser(input);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage(), e);
            throw new RuntimeException("Sign Up Fail");
        }
    }

    @Transactional
    public User createUser(User input) {
        try {
            userRepository.findById(input.getUsername()).ifPresent(user -> {
                throw new RuntimeException("User Already Exists");
            });
            return userRepository.save(setUser(input));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage(), e);
            throw new RuntimeException("Create User Fail");
        }
    }

    @Transactional
    public User updateUser(User input) {
        userRepository.findById(input.getUsername())
                .orElseThrow(() -> new RuntimeException("User Not Exist"));
        return userRepository.save(setUser(input));
    }

    @Transactional
    protected User setUser(User input) {

        // Admin User를 제외한 나머지는 Role에 대한 검사를 진행한다.
        if (!input.getUsername().equals(AuthConstant.ADMIN_USER) &&
                !USER_ROLE_FILTER_SET.contains(input.getRole())) {
            throw new RuntimeException("User Role Not Match");
        }

        User user = userRepository.findById(input.getUsername()).orElse(
                User.builder()
                        .username(input.getUsername())
                        .build()
        );

        if (Objects.nonNull(input.getPassword())) {
            user.setPassword(input.getPassword());
        }
        // Unique Value
        if (Objects.nonNull(input.getEmail())) {
            if (userRepository.existsByEmail(input.getEmail())) {
                throw new RuntimeException("Email Already Exists");
            }
            user.setEmail(input.getEmail());
        }
        // Unique Value
        if (Objects.nonNull(input.getPhoneNumber())) {
            if (userRepository.existsByPhoneNumber(input.getPhoneNumber())) {
                throw new RuntimeException("Phone Number Already Exists");
            }
            user.setPhoneNumber(input.getPhoneNumber());
        }
        return user;
    }

    public String getLoginUserId() {
        return Optional.ofNullable((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .orElseThrow(() -> new RuntimeException("User Not Valid"));
    }
}
