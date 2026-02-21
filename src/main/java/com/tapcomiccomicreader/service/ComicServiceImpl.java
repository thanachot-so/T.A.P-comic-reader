package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.ComicRepository;
import com.tapcomiccomicreader.dto.UpdateComicRequest;
import com.tapcomiccomicreader.entity.Comic;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ComicServiceImpl implements ComicService{

    @Value("${app.comic.location:./comics}")
    private String storageDirectory;

    private final ComicRepository comicRepository;

    @Autowired
    public ComicServiceImpl(ComicRepository comicRepository) {
        this.comicRepository = comicRepository;
    }

    @PostConstruct
    public void init() throws IOException{
        Path storagePath = Paths.get(storageDirectory).toAbsolutePath().normalize();

        Files.createDirectories(storagePath);
    }

    @Override
    public void save(Comic comic) {
        comicRepository.save(comic);
    }

    @Override
    public void deleteById(int id) {
        comicRepository.deleteById(id);
    }

    @Override
    public List<Comic> findAll() {
        return comicRepository.findAll();
    }

    @Override
    public Comic find(int id) {
        return comicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("could not find comic with id - " + id));
    }

    @Override
    public Comic findByUuid(String comicUuid) {
        return comicRepository.findComicByUuid(comicUuid)
                .orElseThrow(() -> new ResourceNotFoundException("could not find comic with uuid " + comicUuid));
    }

    @Override
    public Page<Comic> search(String keyword, int pageNumber) {
        if (keyword == null || keyword.trim().length() < 4) {
            throw new IllegalArgumentException("need more character to search");
        }

        int pageSize = 20;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return comicRepository.searchByTitle(keyword, pageable);
    }

    @Override
    public void uploadCover(int comicId, MultipartFile file) throws IOException {
        var comic = find(comicId);
        String coverName = comic.getUuid() + "_" + file.getOriginalFilename();

        String directory = storageDirectory +"/comics/"+ comicId +"/cover/";
        Path filePath = Paths.get(directory + coverName);

        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        String fileUrl = "/images/comics/"+ comicId + "/cover/" + coverName;

        comic.setCoverUrl(fileUrl);
        comicRepository.save(comic);
    }

    @Override
    public Comic updateComic(int comicId, UpdateComicRequest request) {
        var comic = find(comicId);

        if (request.getName() != null) {
            comic.setTitle(request.getName());
        }
        if (request.getDescription() != null) {
            comic.setDescription(request.getDescription());
        }
        if (request.getAuthor() != null) {
            comic.setAuthor(request.getAuthor());
        }
        if (request.getArtist() != null) {
            comic.setArtist(request.getArtist());
        }

        return comicRepository.save(comic);
    }
}