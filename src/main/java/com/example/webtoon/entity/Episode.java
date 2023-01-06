package com.example.webtoon.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Episode extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long episodeId;

    private String title;

    @ManyToOne
    @JoinColumn(name = "webtoon_id")
    private Webtoon webtoon;

    @OneToOne(fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        mappedBy = "episode")
    private EpisodeFile episodeFile;

    @OneToOne(fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        mappedBy = "episode")
    private EpisodeThumbnail episodeThumbnail;

    @OneToMany(fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        mappedBy = "episode")
    private List<Rate> rates = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        mappedBy = "episode")
    private List<Comment> comments = new ArrayList<>();
}
