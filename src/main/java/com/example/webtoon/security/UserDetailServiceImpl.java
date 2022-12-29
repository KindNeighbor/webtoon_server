package com.example.webtoon.security;

import com.example.webtoon.entity.User;
import com.example.webtoon.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByEmail(email);
        if(!optional.isPresent()) {
            throw new UsernameNotFoundException(email + " 사용자 없음");
        } else {
            User user = optional.get();
            return new SecurityUser(user);
        }
    }
}
