package com.example.webtoon.service;

import com.example.webtoon.dto.WebtoonDto;
import com.example.webtoon.entity.Webtoon;
import com.example.webtoon.entity.WebtoonThumbnail;
import com.example.webtoon.exception.CustomException;
import com.example.webtoon.repository.WebtoonRepository;
import com.example.webtoon.type.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class WebtoonService {

    private final WebtoonRepository webtoonRepository;
    private final FileService fileService;

    // 웹툰 신규 등록
    public WebtoonDto addWebtoon(String title, String artist,
                                 String day, String genre,
                                 MultipartFile file) {

        if (webtoonRepository.existsByTitle(title)) {
            throw new CustomException(
                HttpStatus.BAD_REQUEST, ErrorCode.ALREADY_EXISTED_WEBTOON_TITLE);
        }

        Webtoon webtoon = new Webtoon(title, artist, day, genre);
        WebtoonThumbnail thumbnail = fileService.saveThumbnail(file);
        webtoon.setWebtoonThumbnail(thumbnail);
        thumbnail.setWebtoon(webtoon);
        webtoonRepository.save(webtoon);

        return WebtoonDto.from(webtoon);
    }

    // 웹툰 수정
    public WebtoonDto updateWebtoon(Long webtoonId, String title, String artist,
                                    String day, String genre, MultipartFile file) {

        Webtoon webtoon = webtoonRepository.findByWebtoonId(webtoonId).orElseThrow(() ->
            new CustomException(HttpStatus.NOT_FOUND, ErrorCode.WEBTOON_NOT_FOUND));

        webtoon.setTitle(title);
        webtoon.setArtist(artist);
        webtoon.setDay(day);
        webtoon.setGenre(genre);

        WebtoonThumbnail thumbnail = fileService.saveThumbnail(file);
        webtoon.setWebtoonThumbnail(thumbnail);
        thumbnail.setWebtoon(webtoon);
        webtoonRepository.save(webtoon);

        return WebtoonDto.from(webtoon);
    }

    // 요일로 웹툰 조회
    public List<WebtoonDto> getWebtoonByDay(String day) {
        List<Webtoon> webtoonList = webtoonRepository.findByDayContaining(day);
        System.out.println("webtoonList = " + webtoonList);
        List<WebtoonDto> webtoonDtoList = webtoonList.stream().map(WebtoonDto::from).collect(Collectors.toList());
        return webtoonDtoList;
    }
}
