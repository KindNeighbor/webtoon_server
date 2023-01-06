package com.example.webtoon.repository;

import com.example.webtoon.entity.WebtoonThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebtoonThumbnailRepository extends JpaRepository<WebtoonThumbnail, Integer> {

}
