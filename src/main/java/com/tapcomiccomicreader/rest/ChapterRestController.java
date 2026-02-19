package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.service.ChapterService;
import com.tapcomiccomicreader.service.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/comics")
public class ChapterRestController {
    private final ChapterService chapterService;
    private final ComicService comicService;

    @Autowired
    public ChapterRestController(ChapterService chapterService, ComicService comicService) {
        this.chapterService = chapterService;
        this.comicService = comicService;
    }

    @GetMapping("/{comicUuid}/chapter")
    public ResponseEntity<Object> getChapters(@PathVariable String comicUuid) {
        var comic = comicService.findByUuid(comicUuid);
        return ResponseEntity.ok(chapterService.findAll(comic.getId()));
    }

    @PostMapping("/{comicId}/chapter")
    public ResponseEntity<String> uploadChapterInSequence(
            @PathVariable int comicId,
            @RequestParam("file") List<MultipartFile> files) throws IOException {
        chapterService.uploadChapterInSequence(comicId, files);
        return ResponseEntity.ok("file upload successfully");
    }

    @PostMapping("/{comicId}/chapter/{chapterNum}")
    public ResponseEntity<Object> uploadChapter(@PathVariable int chapterNum,
                                                @PathVariable int comicId,
                                                @RequestParam("file") List<MultipartFile> files) throws IOException{
        chapterService.reUploadChapter(comicId, chapterNum, files);
        return ResponseEntity.ok("file upload successfully");
    }

    @DeleteMapping("/chapter/{chapterId}")
    public ResponseEntity<Object> removeChapterById(
            @PathVariable int chapterId) throws IOException{
        chapterService.remove(chapterId);
        return ResponseEntity.ok("successfully removed chapter id - " + chapterId);
    }
}
