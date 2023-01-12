package com.example.webtoon.service;

import com.example.webtoon.dto.UserDto;
import com.example.webtoon.dto.WebtoonIdListDto;
import com.example.webtoon.entity.User;
import com.example.webtoon.entity.Webtoon;
import com.example.webtoon.exception.CustomException;
import com.example.webtoon.repository.UserRepository;
import com.example.webtoon.repository.WebtoonRepository;
import com.example.webtoon.security.UserPrincipal;
import com.example.webtoon.type.ErrorCode;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WebtoonRepository webtoonRepository;

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

    // 유저가 평점 부여한 웹툰 목록 불러오기
    public List<WebtoonIdListDto> getWebtoonRatedByUser(Long userId) {
        Set<Webtoon> webtoonList = webtoonRepository.findAllByUserId(userId);
        return webtoonList.stream().map(WebtoonIdListDto::from).collect(Collectors.toList());
    }

    // 선호 작품 목록 조회
    public List<WebtoonIdListDto> getFavWebtoonList(Long userId) {
        List<Webtoon> webtoonList = webtoonRepository.findAllByUserID(userId);
        return webtoonList.stream().map(WebtoonIdListDto::from).collect(Collectors.toList());
    }
}
