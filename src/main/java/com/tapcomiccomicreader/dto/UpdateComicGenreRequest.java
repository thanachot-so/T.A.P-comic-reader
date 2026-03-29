package com.tapcomiccomicreader.dto;

import jakarta.validation.constraints.NotNull;

public class UpdateComicGenreRequest {
    @NotNull
    private Integer genreId;

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    @Override
    public String toString() {
        return "UpdateComicGenreRequest{" +
                "genreId=" + genreId +
                '}';
    }
}
