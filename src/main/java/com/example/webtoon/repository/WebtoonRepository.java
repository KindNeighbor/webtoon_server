package com.example.webtoon.repository;

import com.example.webtoon.entity.Webtoon;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

    List<Webtoon> findByDayContaining(String day);
    Boolean existsByTitle(String title);

    @Query(value="Select avg(user_rate) from test_webtoon.rate "
        + "JOIN test_webtoon.episode e on e.episode_id = rate.episode_id "
        + "where webtoon_id = ?1", nativeQuery = true)
    Double getAvgRate(Long webtoonId);

    @Query(value = "SELECT DISTINCT * FROM test_webtoon.webtoon "
        + "LEFT JOIN test_webtoon.episode e on webtoon.webtoon_id = e.webtoon_id "
        + "LEFT JOIN test_webtoon.rate r on e.episode_id = r.episode_id "
        + "WHERE user_id = ?1", nativeQuery = true)
    Set<Webtoon> findAllByUserId(Long userId);

    @Query(value = "SELECT * FROM test_webtoon.webtoon "
        + "LEFT JOIN test_webtoon.favorite f on webtoon.webtoon_id = f.webtoon_id "
        + "WHERE user_id = ?1", nativeQuery = true)
    List<Webtoon> findAllByUserID(Long userId);
}
