package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.ChapterLikeRepository;
import com.tapcomiccomicreader.dao.ChapterRepository;
import com.tapcomiccomicreader.dao.PageRepository;
import com.tapcomiccomicreader.dto.ChapterDTO;
import com.tapcomiccomicreader.entity.Chapter;
import com.tapcomiccomicreader.entity.ChapterLike;
import com.tapcomiccomicreader.entity.Comic;
import com.tapcomiccomicreader.entity.Page;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import com.tapcomiccomicreader.helperclass.SecurityUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ChapterServiceImpl implements ChapterService{
    private final PageService pageService;
    @Value("${app.comic.location:./comics}")
    private String storageDirectory;

    private final ChapterRepository chapterRepository;
    private final PageRepository pageRepository;
    private final ComicService comicService;
    private final ChapterLikeRepository chapterLikeRepository;
    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+)");


    @Autowired
    public ChapterServiceImpl(ChapterRepository chapterRepository, PageRepository pageRepository, ComicService comicService, PageService pageService, ChapterLikeRepository chapterLikeRepository) {
        this.chapterRepository = chapterRepository;
        this.pageRepository = pageRepository;
        this.comicService = comicService;
        this.pageService = pageService;
        this.chapterLikeRepository = chapterLikeRepository;
    }

    @PostConstruct
    public void init() throws IOException {
        Path storagePath = Paths.get(storageDirectory).toAbsolutePath().normalize();

        Files.createDirectories(storagePath);
    }

    @Override
    public List<ChapterDTO> findAll(int comicId) {
        Integer currentUserId = SecurityUtils.getCurrentUserId();

        var chapters = chapterRepository.findByComicId(comicId);

        if (currentUserId == null) {
            return chapters.stream()
                    .map(chapter -> new ChapterDTO(
                            chapter.getId(),
                            chapter.getName(),
                            chapter.getCount(),
                            chapter.getPageCount(),
                            chapter.getLikeCount(),
                            chapter.getDislikeCount(),
                            null,
                            chapter.getCreateAt()
                    )).toList();
        }

        var chapterIds = chapters.stream().map(Chapter::getId).toList();

        var userVotes = chapterLikeRepository.findByUserIdAndChapterIds(currentUserId, chapterIds);

        Map<Integer, Boolean> votes = userVotes.stream()
                .collect(Collectors.toMap(
                        vote -> vote.getChapter().getId(),
                        ChapterLike::isLiked)
                );

        return chapters.stream()
                .map(chapter -> new ChapterDTO(
                        chapter.getId(),
                        chapter.getName(),
                        chapter.getCount(),
                        chapter.getPageCount(),
                        chapter.getLikeCount(),
                        chapter.getDislikeCount(),
                        votes.get(chapter.getId()),
                        chapter.getCreateAt()
                )).toList();
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
    @Transactional
    public void uploadChapterInSequence(int comicId, List<MultipartFile> pages) throws IOException {
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

        savePages(pages, newChapter);
    }

    @Override
    @Transactional
    public void reUploadChapter(int comicId, int chapterNum, List<MultipartFile> pages) throws IOException {
        var chapter = chapterRepository.findByChapterNum(comicId, chapterNum)
                .orElseThrow(() -> new ResourceNotFoundException("could not find the chapter - " + chapterNum));

//        remove old pages
        List<Page> oldPages = new ArrayList<>(chapter.getPages());
        chapter.getPages().clear();
        for (Page page : oldPages) {
            pageService.remove(page.getId());
        }

        savePages(pages, chapter);
    }

    @Override
    @Transactional
    public void remove(int id) throws IOException{
        var chapter = find(id);

        for (var page : chapter.getPages()) {
            pageService.remove(page.getId());
        }

        chapterRepository.delete(chapter);
    }

    private void savePages(List<MultipartFile> pages, Chapter chapter) throws IOException{
        int comicId = chapter.getComic().getId();
        int chapterNum = chapter.getCount();
        String comicUuid = chapter.getComic().getUuid();

        sortPages(pages);

//        page name and upload path
        String pageName = comicUuid +"_"+ chapterNum +"_";
        String UploadPath = storageDirectory +"/comics/"+ comicId +"/Chapters/ch-"+ chapterNum +"/";

//        url relative path
        String relativePath = "/images/comics/"+ comicId + "/chapters/ch-" + chapterNum + "/";

//        create new directory if the path doesn't exist
        Path chapterDir = Paths.get(UploadPath);
        if (!Files.exists(chapterDir)) {
            Files.createDirectories(chapterDir);
        }

//        uploading logic
        int pageCounter = 1;
        for (MultipartFile file : pages) {
//            getting file format
            String originalFileName = file.getOriginalFilename();
            String fileSuffix = originalFileName.substring(originalFileName.lastIndexOf("."));

//            creating file name with page number and file format
            String finalFileName = "page_" + pageCounter + fileSuffix;

//            copy the uploaded file to the local directory
            Path targetPath = Paths.get(UploadPath + pageName + finalFileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

//            initiate the page entity and link it to chapter entity
            Page newPage = new Page();
            newPage.setCount(pageCounter);
            newPage.setChapter(chapter);

//            file url for fetcher to get
            String fileUrl =  relativePath + pageName + finalFileName;

//            saving the data
            newPage.setUrl(fileUrl);

            pageRepository.save(newPage);

            pageCounter++;
        }
    }

    private void sortPages(List<MultipartFile> pages) {
        //        sorting file by number in file name by ascending order
        pages.sort((f1,f2) -> {
            int n1 = extractNumber(f1.getOriginalFilename());
            int n2 = extractNumber(f2.getOriginalFilename());

            return Integer.compare(n1, n2);
        });
    }

    private int extractNumber(String fileName) {
        if (fileName == null) {return Integer.MAX_VALUE;}

        Matcher matcher = NUMBER_PATTERN.matcher(fileName);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return Integer.MAX_VALUE;
    }
}