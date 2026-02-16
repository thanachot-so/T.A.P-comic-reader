package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.service.ChapterService;
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

    @Autowired
    public ChapterRestController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @GetMapping("/{id}/chapter")
    public ResponseEntity<Object> getChapters(@PathVariable int id) {
        return ResponseEntity.ok(chapterService.findAll(id));
    }

    @PostMapping("/{id}/chapter")
    public ResponseEntity<String> uploadChapter(
            @PathVariable int id,
            @RequestParam("file") List<MultipartFile> files) throws IOException {
        chapterService.uploadChapter(id, files);
        return ResponseEntity.ok("file upload successfully");
    }
}
