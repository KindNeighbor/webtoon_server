package com.example.webtoon.controller;

import com.example.webtoon.model.Role;
import com.example.webtoon.model.User;
import com.example.webtoon.repository.UserRepository;
import com.example.webtoon.security.TokenProvider;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public String login(@RequestBody Map<String, String> userMap) {

        User user = userRepository.findByEmail(userMap.get("email"))
            .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(userMap.get("password"), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
        }

        return tokenProvider.createToken(user.getEmail(), user.getRole());
    }

    @PostMapping("/signup")
    public Long register(@RequestBody Map<String, String> userMap) {
        return userRepository.save(User.builder()
            .email(userMap.get("email"))
            .username(userMap.get("username"))
            .password(passwordEncoder.encode(userMap.get("password")))
            .nickname(userMap.get("nickname"))
            .role(Role.ROLE_USER)
            .build()).getUserId();
    }
}
