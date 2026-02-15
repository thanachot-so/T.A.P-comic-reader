package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.entity.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PageService {
    String findPageUrl(String ComicUuid, int chapterNum, int pageNum);

    void replacePage(int comicId, int chapterNum, int pageNum, MultipartFile file) throws IOException;

    Page find(int comicId, int chapterNum, int pageNum);

    Page findById(int pageId);

    void remove(int comicId, int chapterNum, int pageNum) throws IOException;
}
