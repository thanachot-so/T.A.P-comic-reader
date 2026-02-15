package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.ChapterRepository;
import com.tapcomiccomicreader.dao.PageRepository;
import com.tapcomiccomicreader.entity.Chapter;
import com.tapcomiccomicreader.entity.Comic;
import com.tapcomiccomicreader.entity.Page;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChapterServiceImpl implements ChapterService{
    @Value("${app.comic.location:./comics}")
    private String storageDirectory;

    private final ChapterRepository chapterRepository;
    private final PageRepository pageRepository;
    private final ComicService comicService;
    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+)");


    @Autowired
    public ChapterServiceImpl(ChapterRepository chapterRepository, PageRepository pageRepository, ComicService comicService) {
        this.chapterRepository = chapterRepository;
        this.pageRepository = pageRepository;
        this.comicService = comicService;
    }

    @PostConstruct
    public void init() throws IOException {
        Path storagePath = Paths.get(storageDirectory).toAbsolutePath().normalize();

        Files.createDirectories(storagePath);
    }

    @Override
    public List<Chapter> findAll(int comicId) {
        return chapterRepository.findByComicId(comicId);
    }

    @Override
    public void deleteById(int id) {
        chapterRepository.deleteById(id);
    }

    @Override
    public Chapter find(int id) {
        return chapterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("could not find chapter with id - " + id));
    }

    @Override
    public void save(Chapter chapter) {
        chapterRepository.save(chapter);
    }

    @Override
    public void uploadChapter(int comicId, List<MultipartFile> pages) throws IOException {
//        sorting file by number in file name by ascending order
        pages.sort((f1,f2) -> {
            int n1 = extractNumber(f1.getOriginalFilename());
            int n2 = extractNumber(f2.getOriginalFilename());

            return Integer.compare(n1, n2);
        });

//        get the comic by id from parameter
        Comic comic = comicService.find(comicId);

//        get the amount of chapter and increase it by one
        int lastChapterNumber = chapterRepository.findLastChapterNumber(comicId);
        int newChapterNumber = lastChapterNumber + 1;

//        initiating new chapter entity and link it to the comic entity
        Chapter newChapter = new Chapter();
        newChapter.setComic(comic);
        newChapter.setCount(newChapterNumber);

        chapterRepository.save(newChapter);

//        page name and upload path
        String pageName = comic.getUuid() +"_"+ newChapterNumber +"_";
        String chapterUploadPath = storageDirectory +"/comics/"+ comicId +"/Chapters/ch-"+ newChapterNumber +"/";

//        create new directory if the path doesn't exist
        Path chapterDir = Paths.get(chapterUploadPath);
        if (!Files.exists(chapterDir)) {
            Files.createDirectories(chapterDir);
        }

//        uploading logic
        int pageCounter = 1;

        for (MultipartFile file : pages) {
//            getting file format
            String originalFileName = file.getOriginalFilename();
            String fileFormat = originalFileName.substring(originalFileName.lastIndexOf("."));

//            creating file name with page number and file format
            String fileName = "page_" + pageCounter + fileFormat;

//            copy the uploaded file to the directory
            Path filePath = Paths.get(chapterUploadPath + pageName + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

//            initiate the page entity and link it to chapter entity
            Page newPage = new Page();
            newPage.setCount(pageCounter);
            newPage.setChapter(newChapter);

//            file url for fetcher to get
            String fileUrl = "/images/comics/"+ comicId +
                    "/chapters/ch-" + newChapterNumber +
                    "/"+ pageName + fileName;

//            saving the data
            newPage.setUrl(fileUrl);

            pageRepository.save(newPage);

            pageCounter++;
        }
    }

    public int extractNumber(String fileName) {
        if (fileName == null) {return Integer.MAX_VALUE;}

        Matcher matcher = NUMBER_PATTERN.matcher(fileName);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return Integer.MAX_VALUE;
    }
}
