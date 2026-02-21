package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dto.UpdateComicRequest;
import com.tapcomiccomicreader.entity.Comic;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ComicService {
    void save(Comic comic);

    void deleteById(int id);

    List<Comic> findAll();

    Comic find(int id);

    Comic findByUuid(String comicUuid);

    void uploadCover(int comicId, MultipartFile file) throws IOException;

    Comic updateComic(int comicId, UpdateComicRequest request);
}
