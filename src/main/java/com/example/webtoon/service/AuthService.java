package com.example.webtoon.service;

import com.example.webtoon.common.ApiResponse;
import com.example.webtoon.common.ResponseMessage;
import com.example.webtoon.common.StatusCode;
import com.example.webtoon.entity.Role;
import com.example.webtoon.entity.User;
import com.example.webtoon.model.UserInput;
import com.example.webtoon.repository.UserRepository;
import com.example.webtoon.security.TokenProvider;
import com.example.webtoon.security.TokenResponse;
import java.util.Optional;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public ResponseEntity<?> signIn(UserInput parameter) {

        Optional<User> optionalUser = userRepository.findByEmail(parameter.getEmail());

        if (!optionalUser.isPresent()) {
            return ResponseEntity.ok(new ApiResponse().res(StatusCode.INTERNAL_SERVER_ERROR,
                ResponseMessage.LOGIN_FAIL_NO_EXIST_EMAIL));
        }

        User user = optionalUser.get();
        if (!passwordEncoder.matches(parameter.getPassword(), user.getPassword())) {
            return ResponseEntity.ok(new ApiResponse().res(StatusCode.INTERNAL_SERVER_ERROR,
                ResponseMessage.LOGIN_FAIL_PASSWORD_WRONG));
        }

        String token = tokenProvider.createToken(user.getEmail(), user.getRole());

        TokenResponse tokenResponse = new TokenResponse(token);

        return ResponseEntity.ok(new ApiResponse().res(StatusCode.OK,
            ResponseMessage.LOGIN_SUCCESS, tokenResponse));
    }

    public ResponseEntity<?> signUp(UserInput parameter) {

        Optional<User> optionalUser = userRepository.findByEmail(parameter.getEmail());
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(new ApiResponse().res(StatusCode.INTERNAL_SERVER_ERROR,
                ResponseMessage.EXISTED_EMAIL));
        }

        User user = User.builder()
            .email(parameter.getEmail())
            .username(parameter.getUsername())
            .password(passwordEncoder.encode(parameter.getPassword()))
            .nickname(parameter.getNickname())
            .role(Role.ROLE_USER)
            .build();

        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse().res(StatusCode.OK,
            ResponseMessage.CREATED_USER,
            user));
    }
}
