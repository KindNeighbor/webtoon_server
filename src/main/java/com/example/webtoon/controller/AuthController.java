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

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.ok(new ApiResponse(StatusCode.BAD_REQUEST, ResponseMessage.ALREADY_EXISTED_NICKNAME));
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.ok(new ApiResponse(StatusCode.BAD_REQUEST, ResponseMessage.ALREADY_EXISTED_EMAIL));
        }

        // Creating user's account
        User user = new User(signUpRequest.getEmail(), signUpRequest.getUsername(),
                signUpRequest.getPassword(), signUpRequest.getNickname());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException());

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/api/users/{username}")
//                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.ok(new ApiResponse(StatusCode.OK, ResponseMessage.CREATED_USER, result));
    }
}