package com.example.webtoon.controller;


import com.example.webtoon.entity.User;
import com.example.webtoon.payload.UserProfile;
import com.example.webtoon.repository.UserRepository;
import com.example.webtoon.security.CurrentUser;
import com.example.webtoon.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController{

    private final UserRepository userRepository;

    @GetMapping("/user/me")
    public UserProfile getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        System.out.println("currentUser = " + currentUser.getEmail());
        System.out.println("currentUser.getUsername() = " + currentUser.getUsername());
        UserProfile userProfile = new UserProfile(currentUser.getEmail(), currentUser.getUsername(), currentUser.getNickname());
        return userProfile;
    }
    
    @GetMapping("/users/{nickname}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public UserProfile getUserProfile(@PathVariable(value = "nickname") String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException());


        UserProfile userProfile = new UserProfile(user.getEmail(), user.getUsername(), user.getNickname());

        return userProfile;
    }
}