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
    public Page find(int pageId) {
        return pageRepository.findById(pageId)
                .orElseThrow(() -> new ResourceNotFoundException("could not find the page id - " + pageId));
    }

    @Override
    public Page findByRelatedId(int comicId, int chapterNum, int pageNum) {
        return pageRepository.findPage(comicId, chapterNum, pageNum)
                .orElseThrow(() -> new ResourceNotFoundException("page not found"));
    }

    @Transactional
    @Override
    public void remove(int pageId) throws IOException{
        Page page = find(pageId);

        Path targetPath = getPagePath(page);
        Files.delete(targetPath);

        pageRepository.delete(page);
    }

    @Override
    public void replacePage(int pageId, MultipartFile file) throws IOException{
        Page page = find(pageId);
        Path targetPath = getPagePath(page);

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    public Path getPagePath(Page page) {
        String directory = storageDirectory +"/comics/"+ page.getChapter().getComic().getId() +
                "/Chapters/ch-"+
                page.getChapter().getCount();
        String url = page.getUrl();
        String pageName = url.substring(url.lastIndexOf("/"));

        return Paths.get(directory + pageName);
    }
}