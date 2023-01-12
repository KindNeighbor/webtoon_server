package com.example.webtoon.controller;


import com.example.webtoon.dto.ApiResponse;
import com.example.webtoon.dto.WebtoonIdListDto;
import com.example.webtoon.type.ResponseCode;
import com.example.webtoon.dto.UserDto;
import com.example.webtoon.security.CurrentUser;
import com.example.webtoon.security.UserPrincipal;
import com.example.webtoon.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController{

    private final UserService userService;

    // 본인 정보 조회
    @GetMapping("/user/my")
    public ApiResponse<UserDto> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserDto userDto = userService.getCurrentUser(currentUser);
        return new ApiResponse<>(
            HttpStatus.OK, ResponseCode.GET_MY_INFO_SUCCESS, userDto);
    }


    // 회원조회(관리자)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin/user/{nickname}")
    public ApiResponse<UserDto> getUserProfile(@PathVariable(value = "nickname") String nickname) {
        UserDto userDto = userService.getUserInfo(nickname);
        return new ApiResponse<>(
            HttpStatus.OK, ResponseCode.GET_USER_INFO_SUCCESS, userDto);
    }

    // 유저가 평점 부여한 웹툰 목록 조회 (webtoonId 로 반환)
    @GetMapping("/user/webtoon/rated")
    public ApiResponse<List<WebtoonIdListDto>> getWebtoonRatedByUser(@CurrentUser UserPrincipal currentUser) {
        List<WebtoonIdListDto> webtoonList = userService.getWebtoonRatedByUser(currentUser.getId());
        return new ApiResponse<>(
            HttpStatus.OK, ResponseCode.GET_RATED_WEBTOON_LIST_SUCCESS, webtoonList);
    }

    // 선호 작품 목록 조회
    @GetMapping("/user/fav-webtoon")
    public ApiResponse<List<WebtoonIdListDto>> getFavWebtoonList(@CurrentUser UserPrincipal currentUser) {
        List<WebtoonIdListDto> favWebtoonList = userService.getFavWebtoonList(currentUser.getId());
        return new ApiResponse<>(HttpStatus.OK, ResponseCode.GET_FAV_WEBTOON_LIST_SUCCESS, favWebtoonList);
    }
}