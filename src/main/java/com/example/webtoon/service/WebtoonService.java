package com.example.webtoon.service;

import com.example.webtoon.dto.WebtoonDto;
import com.example.webtoon.entity.Webtoon;
import com.example.webtoon.exception.CustomException;
import com.example.webtoon.repository.WebtoonRepository;
import com.example.webtoon.type.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebtoonService {

    private final WebtoonRepository webtoonRepository;

    // 웹툰 신규 등록
    public WebtoonDto addWebtoon(String title, String artist, String day, String genre) {
        if (webtoonRepository.existsByTitle(title)) {
            throw new CustomException(
                HttpStatus.BAD_REQUEST, ErrorCode.ALREADY_EXISTED_WEBTOON_TITLE);
        }

        Webtoon webtoon = new Webtoon(title, artist, day, genre);
        webtoonRepository.save(webtoon);

        return WebtoonDto.builder()
            .title(title)
            .artist(artist)
            .day(day)
            .genre(genre)
            .build();
    }

    // 요일로 웹툰 조회
    public List<WebtoonDto> getWebtoonByDay(String day) {
        List<Webtoon> webtoonList = webtoonRepository.findByDayContaining(day);
        System.out.println("webtoonList = " + webtoonList);
        List<WebtoonDto> webtoonDtoList = webtoonList.stream().map(WebtoonDto::from).collect(Collectors.toList());
        return webtoonDtoList;
    }
}
