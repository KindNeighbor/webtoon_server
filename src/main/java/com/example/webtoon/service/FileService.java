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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import org.apache.tomcat.util.http.fileupload.IOUtils;
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
//    public WebtoonThumbnailDto addWebtoonThumbnail(String title, MultipartFile file) {
//
//        // 제목으로 웹툰 불러옴
//        Webtoon webtoon = webtoonRepository.findByTitle(title).orElseThrow(
//            () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.WEBTOON_TITLE_NOT_FOUND));
//
//        // 썸네일 유무 확인
//        if (webtoon.getWebtoonThumbnail() != null) {
//            throw new CustomException(
//                HttpStatus.CONFLICT, ErrorCode.ALREADY_EXISTED_WEBTOON_THUMBNAIL);
//        }
//
//        WebtoonThumbnail webtoonThumbnail = saveThumbnail(file);
//        webtoonThumbnail.setWebtoon(webtoon);
//        webtoon.setWebtoonThumbnail(webtoonThumbnail);
//        webtoonRepository.save(webtoon);
//
//        return WebtoonThumbnailDto.builder()
//            .fileName(webtoonThumbnail.getFileName())
//            .fileUri(webtoonThumbnail.getFileUri())
//            .build();
//    }

    // 웹툰 썸네일 수정
//    public WebtoonThumbnailDto updateWebtoonThumbnail(Long id, MultipartFile file) {
//
//        WebtoonThumbnail savedFile = saveThumbnail(file);
//
//        // 연결된 웹툰 썸네일
//        WebtoonThumbnail thumbnail = webtoonThumbnailRepository.findByWebtoon_WebtoonId(id)
//            .orElseThrow(() -> new CustomException(
//                HttpStatus.NOT_FOUND, ErrorCode.WEBTOON_THUMBNAIL_NOT_FOUND));
//
//        thumbnail.setFileName(savedFile.getFileName());
//        thumbnail.setFileUri(savedFile.getFileUri());
//
//        webtoonThumbnailRepository.save(thumbnail);
//
//        return WebtoonThumbnailDto.builder()
//            .fileName(thumbnail.getFileName())
//            .fileUri(thumbnail.getFileUri())
//            .build();
//    }

    //파일 저장
    public WebtoonThumbnail saveThumbnail(MultipartFile file) {

        //파일 이름(fileName)
        String fileName = StringUtils.cleanPath(
            Objects.requireNonNull(file.getOriginalFilename()));

        //파일 경로(FileUri)
        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/webtoons/")
            .path(fileName)
            .toUriString();

        InputStream inputStream = null;

        try {
            inputStream = file.getInputStream();

            Path targetLocation = this.dirLocation.resolve(fileName);

            // 이름이 같으면 그위에 덮어씀
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new CustomException(
                HttpStatus.BAD_REQUEST, ErrorCode.FILE_STORAGE_FAILED);

        } finally {
            // InputStream 닫지 않으면 tmp 파일이 지워지지 않아서 UncheckedException 발생
            IOUtils.closeQuietly(inputStream);
        }

        return new WebtoonThumbnail(fileName, fileUri);
    }
}


