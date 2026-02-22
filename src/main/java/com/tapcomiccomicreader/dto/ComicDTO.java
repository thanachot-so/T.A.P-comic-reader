package com.tapcomiccomicreader.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tapcomiccomicreader.entity.Comic;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ComicDTO {
    private String coverUrl;

    @JsonUnwrapped
    private Comic comic;

    public ComicDTO(Comic comic) {
        String domain = ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toUriString();

        coverUrl = domain + comic.getCoverUrl();
        this.comic = comic;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    @Override
    public String toString() {
        return "ComicDTO{" +
                "url='" + coverUrl + '\'' +
                ", comic=" + comic +
                '}';
    }
}
