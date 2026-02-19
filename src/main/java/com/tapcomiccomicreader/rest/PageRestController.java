package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.dto.PageDTO;
import com.tapcomiccomicreader.service.ComicService;
import com.tapcomiccomicreader.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/comics")
public class PageRestController {
    private final ComicService comicService;
    private final PageService pageService;


    @Autowired
    public PageRestController(ComicService comicService, PageService pageService) {
        this.comicService = comicService;
        this.pageService = pageService;
    }

    @GetMapping("/{comicUuid}/{chapterNum}/{pageNum}")
    public ResponseEntity<PageDTO> findComicPage(@PathVariable String comicUuid,
                                                 @PathVariable int chapterNum,
                                                 @PathVariable int pageNum) {
        var comic = comicService.findByUuid(comicUuid);
        var page = pageService.findByRelatedId(comic.getId(), chapterNum, pageNum);
        return ResponseEntity.ok(new PageDTO(page));
    }

    @PutMapping("/chapter/{pageId}")
    public ResponseEntity<String> replacePageFile(
            @RequestParam("file") MultipartFile file,
            @PathVariable int pageId) throws IOException {
        pageService.replacePage(pageId, file);
        return ResponseEntity.ok("file upload successfully");
    }

    @DeleteMapping("/chapter/page/{pageId}")
    public ResponseEntity<String> deletePageFile(
            @PathVariable int pageId) throws IOException {
        pageService.remove(pageId);
        return ResponseEntity.ok("page deleted successfully");
    }
}
