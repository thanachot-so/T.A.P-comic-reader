package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dto.ComicDTO;
import com.tapcomiccomicreader.dto.UpdateComicRequest;
import com.tapcomiccomicreader.entity.Comic;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ComicService {
    void save(Comic comic);

    void deleteById(int id);

    List<Comic> findAll();

    Comic find(int id);

    Comic findByUuid(String comicUuid);

    Page<ComicDTO> search(String keyword, int pageNumber);

    void uploadCover(int comicId, MultipartFile file) throws IOException;

    Comic updateComic(int comicId, UpdateComicRequest request);
}
