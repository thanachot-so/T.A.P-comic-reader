package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.dto.ComicDTO;
import com.tapcomiccomicreader.dto.CreateComicRequest;
import com.tapcomiccomicreader.dto.UpdateComicRequest;
import com.tapcomiccomicreader.entity.Comic;
import com.tapcomiccomicreader.service.ComicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

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

    @GetMapping("/{uuid}")
    public ComicDTO find(@PathVariable String uuid) {
        return new ComicDTO(comicService.findByUuid(uuid));
    }

    @GetMapping("/recent")
    public Page<ComicDTO> findMostRecent(@RequestParam(defaultValue = "0") int page) {
        return comicService.findMostRecent(page);
    }
    @GetMapping("/popular")
    public Page<ComicDTO> findMostPopular(@RequestParam(defaultValue = "0") int page) {
        return comicService.findMostPopular(page);
    }

    @GetMapping("/{uuid}/genres")
    public ResponseEntity<Object> getGenre(@PathVariable String uuid) {
        var genres = comicService.getGenresByComicUuid(uuid);

        return ResponseEntity.ok(genres);
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

    @PutMapping("/{id}/genres")
    public ResponseEntity<Object> updateAllComicGenres(@PathVariable int id,
                                                       @RequestBody Set<Integer> genreIds) {
        comicService.setComicGenres(id,genreIds);

        var comic = comicService.find(id);
        return ResponseEntity.ok(comic);
    }

    @GetMapping("/search")
    public Page<ComicDTO> search(@RequestParam String keyword,
                                 @RequestParam(defaultValue = "0") int page) {
        return comicService.search(keyword, page);
    }

    @GetMapping("/filter")
    public Page<ComicDTO> filter(@RequestParam Set<Integer> genreIds,
                                 @RequestParam(defaultValue = "0") int page) {
        return comicService.searchByGenre(genreIds, page);
    }
}
