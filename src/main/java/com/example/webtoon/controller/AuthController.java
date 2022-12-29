package com.example.webtoon.controller;

import com.example.webtoon.model.UserInput;
import com.example.webtoon.service.AuthService;
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

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody UserInput parameter) {
        return authService.signIn(parameter);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserInput parameter) {
        return authService.signUp(parameter);
    }
}
