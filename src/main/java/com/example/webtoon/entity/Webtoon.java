package com.example.webtoon.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Webtoon extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long webtoonId;

    private String title;

    private String artist;

    private String day;

    private String genre;

    @OneToOne(fetch = FetchType.LAZY,
              cascade = CascadeType.ALL,
              mappedBy = "webtoon")
    private WebtoonThumbnail webtoonThumbnail;

    @OneToMany(fetch = FetchType.LAZY,
               cascade = CascadeType.ALL,
               mappedBy = "webtoon")
    private List<Episode> episodes = new ArrayList<>();
}
