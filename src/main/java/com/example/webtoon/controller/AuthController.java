package com.example.webtoon.controller;


import com.example.webtoon.common.ApiResponse;
import com.example.webtoon.common.ResponseMessage;
import com.example.webtoon.common.StatusCode;
import com.example.webtoon.entity.Role;
import com.example.webtoon.entity.RoleName;
import com.example.webtoon.entity.User;
import com.example.webtoon.payload.JwtAuthenticationResponse;
import com.example.webtoon.payload.LoginRequest;
import com.example.webtoon.payload.SignUpRequest;
import com.example.webtoon.repository.RoleRepository;
import com.example.webtoon.repository.UserRepository;
import com.example.webtoon.security.JwtTokenProvider;
import com.example.webtoon.service.AuthService;
import java.util.Collections;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.signIn(loginRequest);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }
}