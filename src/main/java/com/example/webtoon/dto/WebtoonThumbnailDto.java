package com.example.webtoon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WebtoonThumbnailDto {

    private String fileName;
    private String fileUri;
}
