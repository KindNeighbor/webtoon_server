package com.example.webtoon.service;

import com.example.webtoon.entity.User;
import com.example.webtoon.exception.CustomException;
import com.example.webtoon.type.ErrorCode;
import com.example.webtoon.dto.UserDto;
import com.example.webtoon.repository.UserRepository;
import com.example.webtoon.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 자기 자신 조회
    public UserDto getCurrentUser(UserPrincipal currentUser) {
        return new UserDto(currentUser.getEmail(),
            currentUser.getUsername(),
            currentUser.getNickname());
    }

    // 회원조회(관리자)
    public UserDto getUserInfo(String nickname) {
        User user = userRepository.findByNickname(nickname)
            .orElseThrow(() -> new CustomException(
                HttpStatus.NOT_FOUND, ErrorCode.NICKNAME_NOT_FOUND));

        return new UserDto(user.getEmail(),
                            user.getUsername(),
                            user.getNickname());
    }
}
