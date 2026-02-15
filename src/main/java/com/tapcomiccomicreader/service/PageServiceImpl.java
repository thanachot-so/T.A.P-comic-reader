package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.PageRepository;
import com.tapcomiccomicreader.entity.Page;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class PageServiceImpl implements PageService{

    @Value("${app.comic.location:./comics}")
    private String storageDirectory;

    private final PageRepository pageRepository;

    @Autowired
    public PageServiceImpl(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @PostConstruct
    public void init() throws IOException{
        Path storagePath = Paths.get(storageDirectory).toAbsolutePath().normalize();

        Files.createDirectories(storagePath);
    }

    @Override
    public String findPageUrl(String ComicUuid, int chapterNum, int pageNum) {
        return pageRepository.findPageUrl(ComicUuid, chapterNum, pageNum)
                .orElseThrow(() -> new ResourceNotFoundException("could not find the page - " + pageNum + " or the chapter - " + chapterNum));
    }

    @Override
    public Page find(int comicId, int chapterNum, int pageNum) {
        return pageRepository.findPage(comicId, chapterNum, pageNum)
                .orElseThrow(() -> new ResourceNotFoundException("could not find the page - " + pageNum + " or the chapter - " + chapterNum));
    }

    @Override
    public Page findById(int pageId) {
        return pageRepository.findById(pageId)
                .orElseThrow(() -> new ResourceNotFoundException("could not find the page id - " + pageId));
    }

    @Override
    @Transactional
    public void remove(int comicId, int chapterNum, int pageNum) throws IOException{
        Page page = find(comicId, chapterNum, pageNum);
        Path targetPath = getPagePath(comicId, chapterNum, pageNum);

        Files.deleteIfExists(targetPath);

        pageRepository.delete(page);
    }

    @Override
    public void replacePage(int comicId, int chapterNum, int pageNum, MultipartFile file) throws IOException{
        Path targetPath = getPagePath(comicId, chapterNum, pageNum);

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    public Path getPagePath(int comicId, int chapterNum, int pageNum) {
        Page page = find(comicId, chapterNum, pageNum);

        String directory = storageDirectory + comicId +"/Chapters/ch-"+ chapterNum;
        String url = page.getUrl();
        String pageName = url.substring(url.lastIndexOf("/"));

        return Paths.get(directory + pageName);
    }
}
