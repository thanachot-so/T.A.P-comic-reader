package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dto.PageDTO;
import com.tapcomiccomicreader.entity.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PageService {
    Page find(int pageId);

    Page findByRelatedId(int comicId, int chapterNum, int pageNum);

    void remove(int pageId) throws IOException;

    void replacePage(int pageId, MultipartFile file) throws IOException;

    List<PageDTO> findByComicUuidAndChapterNum(String uuid, int chapterNum);
}
