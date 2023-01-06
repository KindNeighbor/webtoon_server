package com.example.webtoon.service;

import com.example.webtoon.config.FileUploadProperties;
import com.example.webtoon.dto.WebtoonThumbnailDto;
import com.example.webtoon.entity.Webtoon;
import com.example.webtoon.entity.WebtoonThumbnail;
import com.example.webtoon.exception.CustomException;
import com.example.webtoon.repository.WebtoonRepository;
import com.example.webtoon.repository.WebtoonThumbnailRepository;
import com.example.webtoon.type.ErrorCode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Service
public class FileService {

    private final WebtoonRepository webtoonRepository;
    private final WebtoonThumbnailRepository webtoonThumbnailRepository;
    private final Path dirLocation;

    public FileService(WebtoonRepository webtoonRepository,
                       WebtoonThumbnailRepository webtoonThumbnailRepository,
                       FileUploadProperties fileUploadProperties) {
        this.webtoonRepository = webtoonRepository;
        this.webtoonThumbnailRepository = webtoonThumbnailRepository;
        this.dirLocation = Paths.get(fileUploadProperties.getLocation())
            .toAbsolutePath().normalize();
    }

    // 웹툰 썸네일 등록
    public WebtoonThumbnailDto addWebtoonThumbnail(String title, MultipartFile file) {

        Webtoon webtoon = webtoonRepository.findByTitle(title).orElseThrow(
            () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.WEBTOON_TITLE_NOT_FOUND));

//        if (webtoon.getWebtoonThumbnail() != null) {
//            throw new CustomException(
//                HttpStatus.CONFLICT, ErrorCode.ALREADY_EXISTED_WEBTOON_THUMBNAIL_FILENAME);
//        }

        WebtoonThumbnail webtoonThumbnail = saveThumbnail(file);
        webtoonThumbnail.setWebtoon(webtoon);
        webtoon.setWebtoonThumbnail(webtoonThumbnail);
        webtoonThumbnailRepository.save(webtoonThumbnail);

        return WebtoonThumbnailDto.builder()
            .fileName(webtoonThumbnail.getFileName())
            .fileUri(webtoonThumbnail.getFileUri())
            .build();
    }

    //파일 저장
    private WebtoonThumbnail saveThumbnail(MultipartFile file) {

        //파일 이름(fileName)
        String fileName = StringUtils.cleanPath(
            Objects.requireNonNull(file.getOriginalFilename()));

        //파일 경로(FileUri)
        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/webtoons/")
            .path(fileName)
            .toUriString();


        try {
            //동일한 파일 이름이 존재한다면 copy 대체
            Path targetLocation = this.dirLocation.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return new WebtoonThumbnail(fileName, fileUri);

        } catch (IOException ex) {
            throw new CustomException(
                HttpStatus.BAD_REQUEST, ErrorCode.FILE_STORAGE_FAILED);
        }
    }
}


