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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Episode extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long episodeId;

    private String title;

    @Embedded
    private EpisodeFile episodeFile;

    @Embedded
    private EpisodeThumbnail episodeThumbnail;

    @ManyToOne
    @JoinColumn(name = "webtoon_id")
    private Webtoon webtoon;

    @OneToMany(fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        mappedBy = "episode")
    private List<Comment> comments = new ArrayList<>();

    public Episode(String title) {
        this.title = title;
    }
}
