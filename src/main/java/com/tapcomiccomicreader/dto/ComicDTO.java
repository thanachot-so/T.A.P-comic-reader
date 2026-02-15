package com.tapcomiccomicreader.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tapcomiccomicreader.entity.Comic;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ComicDTO {
    private String url;

    @JsonUnwrapped
    private Comic comic;

    public ComicDTO(Comic comic) {
        String domain = ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toUriString();

        url = domain + comic.getCoverUrl();
        this.comic = comic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
                "url='" + url + '\'' +
                ", comic=" + comic +
                '}';
    }
}
