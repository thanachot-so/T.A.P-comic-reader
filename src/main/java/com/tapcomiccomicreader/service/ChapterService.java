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

    void uploadChapterInSequence(int comicId, List<MultipartFile> pages) throws IOException;

    void reUploadChapter(int comicId, int chapterNum, List<MultipartFile> pages) throws IOException;

    void remove(int id) throws IOException;
}
