package com.tapcomiccomicreader.dto;

import com.tapcomiccomicreader.entity.User;

import java.util.List;

public class LibraryDTO {
    List<ComicDTO> comicDTO;

    public LibraryDTO(User user) {
        this.comicDTO = user.getFollowedComics().stream()
                .map(ComicDTO::new)
                .toList();
    }

    public List<ComicDTO> getComicDTO() {
        return comicDTO;
    }

    public void setComicDTO(List<ComicDTO> comicDTO) {
        this.comicDTO = comicDTO;
    }

    @Override
    public String toString() {
        return "LibraryDTO{" +
                "comicDTO=" + comicDTO +
                '}';
    }
}
