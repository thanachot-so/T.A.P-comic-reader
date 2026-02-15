package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ChapterService {
    List<Chapter> findAll(int comicId);

    void deleteById(int id);

    Chapter find(int id);

    void save(Chapter chapter);

    void uploadChapter(int comicId, List<MultipartFile> pages) throws IOException;
}
