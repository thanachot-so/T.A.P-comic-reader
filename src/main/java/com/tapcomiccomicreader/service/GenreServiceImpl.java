package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.ComicRepository;
import com.tapcomiccomicreader.dao.GenreRepository;
import com.tapcomiccomicreader.entity.Genre;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final ComicRepository comicRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, ComicRepository comicRepository) {
        this.genreRepository = genreRepository;
        this.comicRepository = comicRepository;
    }

    @Override
    public void save(String name) {
        var genre = new Genre(name);

        genreRepository.save(genre);
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findById(int genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException("could not find genre id - " + genreId));
    }

    @Override
    @Transactional
    public void updateGenreName(int genreId, String name) {
        var genre = findById(genreId);

        genre.setName(name);
    }

    @Override
    @Transactional
    public void deleteGenre(int genreId) {
        var genre = findById(genreId);
        var comics = comicRepository.findByGenreId(genreId);

        comics.forEach(c -> c.getGenres().remove(genre));

        genreRepository.delete(genre);
    }
}
