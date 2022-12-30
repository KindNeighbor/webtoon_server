package com.example.webtoon.controller;

import com.example.webtoon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController1 {

    private final UserRepository userRepository;

//    @GetMapping("/admin/user/{nickname}")
//    public UserInfo getUserInfo(@PathVariable String nickname) {
//        Optional<User> optionalUser = userRepository.findByNickname(nickname);
//        User user = optionalUser.get();
//        UserInfo userInfo = UserInfo.builder()
//            .email(user.getEmail())
//            .username(user.getUsername())
//            .nickname(user.getNickname())
//            .build();
//
//        return userInfo;
//    }

//    @GetMapping("/user")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public ResponseEntity<UserDto> getMyUserInfo(HttpServletRequest request) {
//        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
//    }
//
//    @GetMapping("/user/{username}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
//        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
//    }
}
