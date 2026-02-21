package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.dto.ComicDTO;
import com.tapcomiccomicreader.dto.CreateComicRequest;
import com.tapcomiccomicreader.dto.UpdateComicRequest;
import com.tapcomiccomicreader.entity.Comic;
import com.tapcomiccomicreader.service.ComicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/comics")
public class ComicRestController {
    private final ComicService comicService;

    @Autowired
    public ComicRestController(ComicService comicService) {
        this.comicService = comicService;
    }

    @GetMapping
    public List<ComicDTO> findAll() {
        return comicService.findAll().stream()
                .map(ComicDTO::new)
                .toList();
    }

    @PostMapping("/{id}/cover")
    public ResponseEntity<String> uploadCover(
            @PathVariable int id,
            @RequestParam("file") MultipartFile file) throws IOException {
            comicService.uploadCover(id, file);
            return ResponseEntity.ok("file upload successfully");
    }

    @PostMapping
    public ResponseEntity<Object> createComic(@RequestBody @Valid CreateComicRequest request) {
        comicService.save(new Comic(request.getName(),
                request.getDescription(),
                request.getAuthor(),
                request.getArtist()));

        return ResponseEntity.ok("comic: " + request.getName() + " has been created");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateComic(@PathVariable int id,
                                              @RequestBody UpdateComicRequest request) {
        var updatedComic = comicService.updateComic(id, request);
        return ResponseEntity.ok(updatedComic);
    }
}
