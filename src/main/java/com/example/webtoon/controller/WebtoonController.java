package com.example.webtoon.controller;

import com.example.webtoon.dto.ApiResponse;
import com.example.webtoon.dto.WebtoonDto;
import com.example.webtoon.repository.WebtoonRepository;
import com.example.webtoon.service.WebtoonService;
import com.example.webtoon.type.ResponseCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class WebtoonController {

    private final WebtoonService webtoonService;

    // 웹툰 등록
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/add-webtoon")
    public ApiResponse<?> addWebtoon(@RequestParam String title,
                              @RequestParam String artist,
                              @RequestParam String day,
                              @RequestParam String genre) {

        WebtoonDto webtoonDto = webtoonService.addWebtoon(title, artist, day, genre);
        return new ApiResponse<>(
            HttpStatus.OK, ResponseCode.CREATE_NEW_WEBTOON, webtoonDto);
    }

    // 요일로 웹툰 조회
    @GetMapping("/get-webtoon/day")
    public ApiResponse<?> getWebtoonByDay(@RequestParam String day) {
        List<WebtoonDto> webtoonByDay = webtoonService.getWebtoonByDay(day);
        return new ApiResponse<>(
            HttpStatus.OK, ResponseCode.CREATE_NEW_WEBTOON, webtoonByDay);
    }
}
