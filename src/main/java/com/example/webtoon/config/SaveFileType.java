package com.example.webtoon.config;


import com.example.webtoon.entity.WebtoonThumbnail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveFileType<T> {

    private String fileName;
    private String fileUri;

    public SaveFileType(String fileName, String fileUri) {
        this.fileName = fileName;
        this.fileUri = fileUri;
    }
}
