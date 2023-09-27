package com.app.trackmeuser.controller;

import com.app.trackmeuser.model.UserRoleType;
import com.app.trackmeuser.model.dto.SignUpRequestDTO;
import com.app.trackmeuser.model.dto.UserLoginRequestDTO;
import com.app.trackmeuser.model.dto.UserTokenResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private String accessToken;
    private String refreshToken;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("회원가입 테스트")
    void signUp() throws Exception {
        SignUpRequestDTO requestDTO = SignUpRequestDTO.builder()
                .username("wlsgus555")
                .email("wlsgus555@gmail.com")
                .password("Abc123~!")
                .phoneNumber("010-1234-5678")
                .role(UserRoleType.ROLE_CUSTOMER)
                .build();

        String request = mapper.writeValueAsString(requestDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/sign-up")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("wlsgus555"))
                .andExpect(jsonPath("$.email").value("wlsgus555@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("010-1234-5678"))
                .andExpect(jsonPath("$.role").value("ROLE_CUSTOMER"));
    }

    @Test
    @DisplayName("로그인 테스트")
    void login() throws Exception {
        signUp();
        UserLoginRequestDTO requestDTO = UserLoginRequestDTO.builder()
                .username("wlsgus555")
                .password("Abc123~!")
                .build();

        String request = mapper.writeValueAsString(requestDTO);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        UserTokenResponseDTO response = mapper.readValue(content, UserTokenResponseDTO.class);
        accessToken = response.getAccessToken();
        refreshToken = response.getRefreshToken();
    }

    @Test
    @DisplayName("인증/인가 테스트")
    void authentication_and_authorization() throws Exception {
        login();
        mockMvc.perform(MockMvcRequestBuilders.get("/demo")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }
}