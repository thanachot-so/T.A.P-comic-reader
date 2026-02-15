package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.dto.ComicDTO;
import com.tapcomiccomicreader.dto.CreateComicRequest;
import com.tapcomiccomicreader.dto.PageDTO;
import com.tapcomiccomicreader.entity.Comic;
import com.tapcomiccomicreader.service.ChapterService;
import com.tapcomiccomicreader.service.ComicService;
import com.tapcomiccomicreader.service.PageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ComicRestController {
    private final ComicService comicService;
    private final ChapterService chapterService;
    private final PageService pageService;

    @Autowired
    public ComicRestController(ComicService comicService, ChapterService chapterService, PageService pageService) {
        this.comicService = comicService;
        this.chapterService = chapterService;
        this.pageService = pageService;
    }

    @GetMapping("/comics")
    public List<ComicDTO> findAll() {
        return comicService.findAll().stream()
                .map(ComicDTO::new)
                .toList();
    }

    @GetMapping("/comics/{comicUuid}/{chapterNum}/{pageNum}")
    public ResponseEntity<PageDTO> findComicPage(@PathVariable String comicUuid,
                                @PathVariable int chapterNum,
                                @PathVariable int pageNum) {
            var comic = comicService.findByUuid(comicUuid);
            var page = pageService.find(comic.getId(), chapterNum, pageNum);
            return ResponseEntity.ok(new PageDTO(page));
    }

    @PostMapping("/comics/{id}/cover")
    public ResponseEntity<String> uploadCover(
            @PathVariable int id,
            @RequestParam("file") MultipartFile file) throws IOException {
            comicService.uploadCover(id, file);
            return ResponseEntity.ok("file upload successfully");
    }

    @PostMapping("/comics")
    public ResponseEntity<Object> createComic(@RequestBody @Valid CreateComicRequest request) {
        comicService.save(new Comic(request.getName(),
                request.getDescription(),
                request.getAuthor(),
                request.getArtist()));

        return ResponseEntity.ok("comic: " + request.getName() + " has been created");
    }

    @PostMapping("/comics/{id}/chapter")
    public ResponseEntity<String> uploadChapter(
            @PathVariable int id,
            @RequestParam("file") List<MultipartFile> files) throws IOException {
            chapterService.uploadChapter(id, files);
            return ResponseEntity.ok("file upload successfully");
    }

    @PutMapping("/comics/{id}/{chapter}/{page}")
    public ResponseEntity<String> replacePageFile(
            @RequestParam("file") MultipartFile file,
            @PathVariable int id,
            @PathVariable int chapter,
            @PathVariable int page) throws IOException {
            pageService.replacePage(id, chapter, page, file);
            return ResponseEntity.ok("file upload successfully");
    }

    @DeleteMapping("/comics/{id}/{chapter}/{page}")
    public ResponseEntity<String> deletePageFile(
            @PathVariable int id,
            @PathVariable int chapter,
            @PathVariable int page) throws IOException {
            pageService.remove(id, chapter, page);
            return ResponseEntity.ok("page deleted successfully");
    }
}
