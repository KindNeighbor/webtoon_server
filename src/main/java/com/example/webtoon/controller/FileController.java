package com.example.webtoon.controller;

import com.example.webtoon.dto.ApiResponse;
import com.example.webtoon.dto.WebtoonThumbnailDto;
import com.example.webtoon.service.FileService;
import com.example.webtoon.type.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    // 신규 웹툰 썸네일 등록
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/add-webtoon-thumbnail")
    public ApiResponse<?> addWebtoonThumbnail(@RequestParam String title,
                                              @RequestParam MultipartFile file) {
        WebtoonThumbnailDto thumbnail = fileService.addWebtoonThumbnail(title, file);
        return new ApiResponse<>(
            HttpStatus.OK, ResponseCode.CREATE_NEW_WEBTOON_THUMBNAIL, thumbnail);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update-webtoon-thumbnail")
    public ApiResponse<?> updateWebtoonThumbnail(@RequestParam String title,
                                                 @RequestParam MultipartFile file) {
        WebtoonThumbnailDto thumbnail = fileService.addWebtoonThumbnail(title, file);
        return new ApiResponse<>(
            HttpStatus.OK, ResponseCode.CREATE_NEW_WEBTOON_THUMBNAIL, thumbnail);
    }
}
