package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.entity.Genre;

import java.util.List;

public interface GenreService {
    void save(String name);

    List<Genre> findAll();

    Genre findById(int genreId);

    void updateGenreName(int genreId, String name);

    void deleteGenre(int genreId);
}
