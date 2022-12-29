package com.example.webtoon.security;

import com.example.webtoon.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class SecurityUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public SecurityUser(User user) {
        super(user.getUserId().toString(), user.getPassword(),
            AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
