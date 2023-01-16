package com.example.webtoon.repository;

import com.example.webtoon.entity.Episode;
import java.lang.reflect.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    List<Episode> findByWebtoonWebtoonId(Long webtoonId);

    Boolean existsByWebtoon_WebtoonIdAndTitle(Long webtoonId, String title);

    @Query(value="Select avg(user_rate) from test_webtoon.rate where episode_id = ?1", nativeQuery = true)
    Double getAvgRate(Long id);
}
