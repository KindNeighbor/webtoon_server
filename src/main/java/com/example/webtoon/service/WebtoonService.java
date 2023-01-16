package com.example.webtoon.service;

import com.example.webtoon.dto.EpisodeDto;
import com.example.webtoon.dto.WebtoonDto;
import com.example.webtoon.entity.Episode;
import com.example.webtoon.entity.Webtoon;
import com.example.webtoon.entity.WebtoonThumbnail;
import com.example.webtoon.exception.CustomException;
import com.example.webtoon.repository.EpisodeRepository;
import com.example.webtoon.repository.WebtoonRepository;
import com.example.webtoon.type.ErrorCode;
import com.example.webtoon.type.SortType;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class WebtoonService {

    private final int SIZE = 10;

    private final WebtoonRepository webtoonRepository;
    private final EpisodeRepository episodeRepository;
    private final FileService fileService;

    // 웹툰 신규 등록
    public WebtoonDto addWebtoon(String title, String artist,
                                 String day, String genre,
                                 MultipartFile file) throws IOException {

        if (webtoonRepository.existsByTitle(title)) {
            throw new CustomException(
                HttpStatus.CONFLICT, ErrorCode.ALREADY_EXIST_WEBTOON_TITLE);
        }

        Webtoon webtoon = new Webtoon(title, artist, day, genre);
        WebtoonThumbnail thumbnail = fileService.saveWebtoonThumbnailFile(file);
        webtoon.setWebtoonThumbnail(thumbnail);
        webtoonRepository.save(webtoon);

        return WebtoonDto.from(webtoon);
    }

    // 웹툰 수정
    public WebtoonDto updateWebtoon(Long webtoonId,
                                    String title, String artist,
                                    String day, String genre,
                                    MultipartFile file) throws IOException {

        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(() ->
            new CustomException(HttpStatus.NOT_FOUND, ErrorCode.WEBTOON_NOT_FOUND));

        webtoon.setTitle(title);
        webtoon.setArtist(artist);
        webtoon.setDay(day);
        webtoon.setGenre(genre);

        WebtoonThumbnail thumbnail = fileService.saveWebtoonThumbnailFile(file);
        webtoon.setWebtoonThumbnail(thumbnail);
        webtoonRepository.save(webtoon);

        return WebtoonDto.from(webtoon);
    }

    // 웹툰 삭제
    public void deleteWebtoon(Long webtoonId) {
        if (!webtoonRepository.existsById(webtoonId)) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.WEBTOON_NOT_FOUND);
        }
        webtoonRepository.deleteById(webtoonId);
    }

    // 에피소드 신규 등록
    public EpisodeDto addEpisode(Long webtoonId,
                                 String title,
                                 MultipartFile epFile,
                                 MultipartFile thFile) throws IOException {

        if (episodeRepository.existsByWebtoon_WebtoonIdAndTitle(webtoonId, title)) {
            throw new CustomException(
                HttpStatus.CONFLICT, ErrorCode.ALREADY_EXIST_EPISODE_TITLE);
        }

        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow(
            () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.WEBTOON_NOT_FOUND));
        Episode episode = new Episode(title);
        episode.setEpisodeFile(fileService.saveEpisodeFile(epFile));
        episode.setEpisodeThumbnail(fileService.saveEpisodeThumbnailFile(thFile));
        episode.setWebtoon(webtoon);
        episodeRepository.save(episode);

        return EpisodeDto.from(episode);
    }

    // 에피소드 수정
    public EpisodeDto updateEpisode(Long episodeId,
                                    String title,
                                    MultipartFile epFile,
                                    MultipartFile thFile) throws IOException {

        Episode episode = episodeRepository.findById(episodeId).orElseThrow(
            () -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.EPISODE_NOT_FOUND));

        episode.setTitle(title);
        episode.setEpisodeFile(fileService.saveEpisodeFile(epFile));
        episode.setEpisodeThumbnail(fileService.saveEpisodeThumbnailFile(thFile));
        episodeRepository.save(episode);

        return EpisodeDto.from(episode);
    }

    // 에피소드 삭제
    public void deleteEpisode(Long episodeId) {
        if (!episodeRepository.existsById(episodeId)) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.EPISODE_NOT_FOUND);
        }
        episodeRepository.deleteById(episodeId);
    }

    // 요일로 웹툰 조회
//    public List<WebtoonDto> getWebtoonByDay(String day) {
//        List<Webtoon> webtoonList = webtoonRepository.findByDayContaining(day);
//        return webtoonList.stream().map(WebtoonDto::from).collect(Collectors.toList());
//    }

    // 웹툰별 에피소드 목록 조회
    public List<EpisodeDto> getWebtoonEpisodes(Long webtoonId) {
        List<Episode> episodeList = episodeRepository.findByWebtoonWebtoonId(webtoonId);
        Webtoon webtoon = webtoonRepository.findById(webtoonId).orElseThrow();
        webtoon.setCount(webtoon.getCount() + 1);
        webtoonRepository.save(webtoon);
        return episodeList.stream().map(EpisodeDto::from).collect(Collectors.toList());
    }
    // 에러코드 고치시오
    public Page<WebtoonDto> getWebtoonByDay(String day, String orderType, Integer page) {

        if (!webtoonRepository.existsByDayContaining(day)) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.WEBTOON_NOT_FOUND);
        }

        Optional<SortType> optionalOrder = Arrays.stream(SortType.values()).filter(
            it -> it.getType().equals(orderType)).findFirst();

        if (optionalOrder.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.ORDER_TYPE_NOT_FOUND);
        }

        Sort sort = SortType.getSort(optionalOrder.get());
        Pageable pageable = PageRequest.of(page, SIZE, sort);
        Page<Webtoon> webtoons = webtoonRepository.findByDay(day, pageable);

        return webtoons.map(WebtoonDto::from);
    }

}
