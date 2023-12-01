package com.tsystems.pablo_canton.railway.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.pablo_canton.railway.business.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthRestAPIV1ITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthRestAPIV1 authRestAPIV1;

    @Test
    void testLogin_WhenLoginOk() throws Exception {
        LoginRequestDto dto = LoginRequestDto.builder().username("test.client").password("test").build();

        mvc.perform(post("/api/v1/auth/login")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.role").value("C"));
    }

    @Test
    void testLogin_WhenUsernameNotExists() throws Exception {
        LoginRequestDto dto = LoginRequestDto.builder().username("wrong").password("test").build();

        mvc.perform(post("/api/v1/auth/login")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found wrong"));
    }

    @Test
    void testLogin_WhenWrongPassword() throws Exception {
        LoginRequestDto dto = LoginRequestDto.builder().username("test.client").password("wrong").build();

        mvc.perform(post("/api/v1/auth/login")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string("User password does not match"));
    }

    @Test
    void testSignUp_WhenUserOk() throws Exception {
        UserDTO dto = UserDTO.builder().username("sing-up-test").password("test").role("C").build();

        mvc.perform(post("/api/v1/auth/sing-up")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.role").value("C"));
    }

    @Test
    void testSignUp_WhenUsernameNotAvailable() throws Exception {
        UserDTO dto = UserDTO.builder().username("test.client").password("test").role("C").build();

        mvc.perform(post("/api/v1/auth/sing-up")
                        .contextPath("/api")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string("Username test.client is not available"));
    }

    @Test
    void testAuthorizedRequest() throws Exception {
        AuthDTO login = authRestAPIV1.login(LoginRequestDto.builder().username("test.client").password("test").build());

        mvc.perform(get("/api/v1/clients/station_names")
                        .contextPath("/api")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + login.getToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Madrid"));
    }
}