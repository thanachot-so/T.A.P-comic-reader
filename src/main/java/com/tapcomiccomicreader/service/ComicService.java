package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dto.ComicDTO;
import com.tapcomiccomicreader.dto.UpdateComicRequest;
import com.tapcomiccomicreader.entity.Comic;
import com.tapcomiccomicreader.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ComicService {
    void save(Comic comic);

    void deleteById(int id);

    List<Comic> findAll();

    Comic find(int id);

    Page<ComicDTO> findMostRecent(int page);

    Page<ComicDTO> findMostPopular(int page);

    Comic findByUuid(String comicUuid);

    Page<ComicDTO> search(String keyword, int pageNumber);

    Page<ComicDTO> searchByGenre(Set<Integer> genreIds, int pageNumber);

    Set<Genre> getGenresByComicUuid(String comicUuid);

    void setComicGenres(int comicId, Set<Integer> genreIds);

    void uploadCover(int comicId, MultipartFile file) throws IOException;

    Comic updateComic(int comicId, UpdateComicRequest request);
}
