package com.example.webtoon.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class WebtoonThumbnail extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wtId;

    private String fileName;

    private String fileUri;

    @OneToOne
    @JoinColumn(name = "webtoon_id")
    private Webtoon webtoon;
}
