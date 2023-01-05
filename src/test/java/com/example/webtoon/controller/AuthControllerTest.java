package com.example.webtoon.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webtoon.config.SecurityConfig;
import com.example.webtoon.dto.LoginRequest;
import com.example.webtoon.dto.SignUpRequest;
import com.example.webtoon.exception.CustomException;
import com.example.webtoon.service.AuthService;
import com.example.webtoon.type.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = AuthController.class, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthService authService;


    // 로그인 컨트롤러
    @Test
    @WithMockUser
    @DisplayName("로그인 성공")
    void signInControllerSuccess() throws Exception {
        // given
        LoginRequest loginRequest =
            new LoginRequest("test@test.com", "testPassword");

        given(authService.signIn(any())).willReturn("token");

        // when
        // then
        mockMvc.perform(post("/api/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .with(csrf())
            .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("200"))
            .andExpect(jsonPath("$.statusMessage").value("OK"))
            .andExpect(jsonPath("$.responseCode").value("LOGIN_SUCCESS"))
            .andExpect(jsonPath("$.responseMessage").value("로그인 성공"))
            .andExpect(jsonPath("$.data.accessToken").value("token"))
            .andExpect(jsonPath("$.data.tokenType").value("Bearer"))
            .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("로그인 실패 - 일치하는 이메일 정보 없음")
    void signInControllerFailed_EmailNotFound() throws Exception {
        // given
        LoginRequest loginRequest =
            new LoginRequest("test@test.com", "testPassword");

        given(authService.signIn(any())).willThrow(
            new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.LOGIN_FAIL_EMAIL_NOT_EXIST));

        // when
        // then
        mockMvc.perform(post("/api/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value("400"))
            .andExpect(jsonPath("$.statusMessage").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.errorCode").value("LOGIN_FAIL_EMAIL_NOT_EXIST"))
            .andExpect(jsonPath("$.errorMessage").value("가입된 이메일 주소가 없습니다."))
            .andDo(print());
    }

    // 회원가입 컨트롤러
    @Test
    @WithMockUser
    @DisplayName("회원가입 성공")
    void signUpControllerSuccess() throws Exception {
        // given
        SignUpRequest signUpRequest = new SignUpRequest(
            "test@test.com", "testUsername",
            "testPassword", "testNickname");

        // when
        // then
        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(objectMapper.writeValueAsString(signUpRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("200"))
            .andExpect(jsonPath("$.statusMessage").value("OK"))
            .andExpect(jsonPath("$.responseCode").value("CREATED_USER"))
            .andExpect(jsonPath("$.responseMessage").value("회원 가입 성공"))
            .andExpect(jsonPath("$.data.email").value("test@test.com"))
            .andExpect(jsonPath("$.data.username").value("testUsername"))
            .andExpect(jsonPath("$.data.password").value("testPassword"))
            .andExpect(jsonPath("$.data.nickname").value("testNickname"))
            .andDo(print());
    }
}