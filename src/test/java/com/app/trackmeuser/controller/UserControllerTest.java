package com.app.trackmeuser.controller;

import com.app.trackmeuser.model.UserRoleType;
import com.app.trackmeuser.model.dto.SignUpRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("test01")
    void test01() throws Exception {
        SignUpRequestDTO requestDTO = SignUpRequestDTO.builder()
                .username("wlsgus555")
                .email("wlsgus555@gmail.com")
                .password("Abc123~!")
                .phoneNumber("010-1234-5678")
                .role(UserRoleType.ROLE_CUSTOMER)
                .build();

        String request = mapper.writeValueAsString(requestDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/sing-up")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("wlsgus555"))
                .andExpect(jsonPath("$.email").value("wlsgus555@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("010-1234-5678"))
                .andExpect(jsonPath("$.role").value("ROLE_CUSTOMER"));
    }
}