package com.example.webtoon.controller;


import com.example.webtoon.payload.ApiResponse;
import com.example.webtoon.payload.JwtAuthenticationResponse;
import com.example.webtoon.payload.LoginRequest;
import com.example.webtoon.type.ResponseCode;
import com.example.webtoon.payload.SignUpRequest;
import com.example.webtoon.service.AuthService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 로그인
    @PostMapping("/signin")
    public ApiResponse<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        String jwt = authService.signIn(loginRequest);
        return new ApiResponse<>(
            HttpStatus.OK, ResponseCode.LOGIN_SUCCESS, new JwtAuthenticationResponse(jwt));
    }

    // 회원가입
    @PostMapping("/signup")
    public ApiResponse<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        authService.signUp(signUpRequest);
        return new ApiResponse<>(
            HttpStatus.OK, ResponseCode.CREATED_USER, signUpRequest);
    }
}