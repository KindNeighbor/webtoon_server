package com.example.webtoon.entity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class WebtoonThumbnail extends DateEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wtId;

    private String fileName;

    private String fileUri;

    @OneToOne
    @JoinColumn(name = "webtoon_id", unique = true)
    private Webtoon webtoon;

    public WebtoonThumbnail(String fileName, String fileUri) {
        this.fileName = fileName;
        this.fileUri = fileUri;
    }
}
