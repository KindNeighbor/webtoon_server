package com.example.webtoon.controller;

import com.example.webtoon.service.AuthService1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController1 {

    private final AuthService1 authService;

//    @PostMapping("/signin1")
//    public ResponseEntity<?> signIn(@RequestBody UserInput parameter) {
//        return authService.signIn(parameter);
//    }
//
//    @PostMapping("/signup1")
//    public ResponseEntity<?> signUp(@RequestBody UserInput parameter) {
//        return authService.signUp(parameter);
//    }
}
