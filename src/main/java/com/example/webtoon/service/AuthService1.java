package com.example.webtoon.service;

import com.example.webtoon.repository.UserRepository;
import com.example.webtoon.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService1 {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

//    public ResponseEntity<?> signIn(UserInput parameter) {
//
//        Optional<User> optionalUser = userRepository.findOneWithRolesByEmail(parameter.getEmail());
//
//        if (!optionalUser.isPresent()) {
//            return ResponseEntity.ok(new ApiResponse().res(StatusCode.BAD_REQUEST,
//                ResponseMessage.LOGIN_FAIL_NO_EMAIL_EXIST));
//        }
//
//        User user = optionalUser.get();
//        if (!passwordEncoder.matches(parameter.getPassword(), user.getPassword())) {
//            return ResponseEntity.ok(new ApiResponse().res(StatusCode.BAD_REQUEST,
//                ResponseMessage.LOGIN_FAIL_PASSWORD_WRONG));
//        }
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//            new UsernamePasswordAuthenticationToken(parameter.getEmail(), parameter.getPassword());
//
//        Authentication authentication =
//            authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        String token = tokenProvider.createToken(authentication);
//
//        TokenResponse tokenResponse = new TokenResponse(token);
//
//        return ResponseEntity.ok(new ApiResponse().res(StatusCode.OK,
//            ResponseMessage.LOGIN_SUCCESS, tokenResponse));
//    }
//
//    public ResponseEntity<?> signUp(UserInput parameter) {
//
//        Optional<User> checkEmail = userRepository.findOneWithRolesByEmail(parameter.getEmail());
//        if (checkEmail.isPresent()) {
//            return ResponseEntity.ok(new ApiResponse().res(StatusCode.INTERNAL_SERVER_ERROR,
//                ResponseMessage.ALREADY_EXISTED_EMAIL));
//        }
//
//        Optional<User> checkNickName = userRepository.findByNickname(parameter.getNickname());
//        if (checkNickName.isPresent()) {
//            return ResponseEntity.ok(new ApiResponse().res(StatusCode.INTERNAL_SERVER_ERROR,
//                ResponseMessage.ALREADY_EXISTED_NICKNAME));
//        }
//
//        Role role = Role.builder()
//            .roleName("ROlE_USER")
//            .build();
//
//        User user = User.builder()
//            .email(parameter.getEmail())
//            .username(parameter.getUsername())
//            .password(passwordEncoder.encode(parameter.getPassword()))
//            .nickname(parameter.getNickname())
//            .roles(Collections.singleton(role))
//            .build();
//
//        userRepository.save(user);
//
//        return ResponseEntity.ok(new ApiResponse().res(StatusCode.OK,
//            ResponseMessage.CREATED_USER,
//            user));
//    }
}
