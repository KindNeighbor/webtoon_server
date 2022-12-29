package com.example.webtoon.model;

import com.example.webtoon.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput {

    private Long userId;
    private String email;
    private String username;
    private String password;
    private String nickname;
    private Role role;
}
