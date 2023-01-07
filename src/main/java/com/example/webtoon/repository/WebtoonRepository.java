package com.example.webtoon.repository;

import com.example.webtoon.entity.Webtoon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon, Integer> {

    Optional<Webtoon> findByWebtoonId(Long Id);
    List<Webtoon> findByDayContaining(String day);

    Optional<Webtoon> findByTitle(String title);

    Boolean existsByTitle(String title);
}