package com.example.webtoon.service;

import com.example.webtoon.entity.Authority;
import com.example.webtoon.entity.User;
import com.example.webtoon.model.UserDto;
import com.example.webtoon.repository.UserRepository;
import com.example.webtoon.security.SecurityUtil;
import java.util.Collections;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        if (userRepository.findOneWithAuthoritiesByNickname(userDto.getNickname()).orElse(null) != null) {
            throw new RuntimeException("중복되는 닉네임입니다.");
        }

        Authority authority = Authority.builder()
            .authorityName("ROLE_USER")
            .build();

        User user = User.builder()
            .email(userDto.getEmail())
            .username(userDto.getUsername())
            .password(passwordEncoder.encode(userDto.getPassword()))
            .nickname(userDto.getNickname())
            .authorities(Collections.singleton(authority))
            .activated(true)
            .build();

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String nickname) {
        return userRepository.findOneWithAuthoritiesByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentNickname().flatMap(userRepository::findOneWithAuthoritiesByNickname);
    }
}