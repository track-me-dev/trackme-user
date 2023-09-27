package com.app.trackmeuser.controller;

import com.app.trackmeuser.model.User;
import com.app.trackmeuser.model.dto.*;
import com.app.trackmeuser.model.mapper.UserMapper;
import com.app.trackmeuser.service.TokenService;
import com.app.trackmeuser.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final TokenService tokenService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<UserTokenResponseDTO> login(UserLoginRequestDTO loginDTO) {

        try {
            userService.signIn(loginDTO);
            return new ResponseEntity<>(
                    UserTokenResponseDTO.builder()
                            .accessToken(tokenService.genAccessToken(loginDTO.getUsername()))
                            .refreshToken(tokenService.genRefreshToken(loginDTO.getUsername()))
                            .build(),
                    HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDTO> signUp(SignUpRequestDTO signUpRequestDTO) {
        try {
            User input = userMapper.toEntity(signUpRequestDTO);
            User user = userService.signUp(input);
            return new ResponseEntity<>(userMapper.toDTO(user), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUserList() {

        List<User> userList = userService.getUserList();
        return new ResponseEntity<>(userList.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/token")
    public ResponseEntity<UserTokenResponseDTO> updateAccessToken(UserTokenUpdateRequestDTO userTokenUpdateRequestDTO) {
        try {
            log.debug("TokenUpdate {}", userTokenUpdateRequestDTO);
            return new ResponseEntity<>(
                    UserTokenResponseDTO.builder()
                            .accessToken(tokenService.genAccessTokenByRefreshToken(userTokenUpdateRequestDTO.getRefreshToken()))
                            .refreshToken(userTokenUpdateRequestDTO.getRefreshToken())
                            .build(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Token Update Fail");
        }
    }
}
