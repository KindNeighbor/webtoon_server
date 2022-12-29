package com.example.webtoon.controller;

import com.example.webtoon.common.ApiResponse;
import com.example.webtoon.common.ResponseMessage;
import com.example.webtoon.common.StatusCode;
import com.example.webtoon.entity.User;
import com.example.webtoon.model.UserInput;
import com.example.webtoon.repository.UserRepository;
import com.example.webtoon.security.TokenResponse;
import com.example.webtoon.service.AuthService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody UserInput parameter) {
        ResponseEntity<?> responseEntity = authService.signIn(parameter);
        return responseEntity;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserInput parameter) {
        ResponseEntity responseEntity = authService.signUp(parameter);
        return responseEntity;
    }
}
