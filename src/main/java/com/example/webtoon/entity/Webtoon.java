package com.example.webtoon.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Webtoon extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long webtoonId;

    @Column(unique = true)
    private String title;

    private String artist;
    
    private String day;

    private String genre;

    private Long count;

    @Embedded
    private WebtoonThumbnail webtoonThumbnail;

    @OneToMany(fetch = FetchType.LAZY,
               cascade = CascadeType.ALL,
               mappedBy = "webtoon")
    private List<Episode> episodes = new ArrayList<>();

    public Webtoon(String title, String artist, String day, String genre) {
        this.title = title;
        this.artist = artist;
        this.day = day;
        this.genre = genre;
    }
}
