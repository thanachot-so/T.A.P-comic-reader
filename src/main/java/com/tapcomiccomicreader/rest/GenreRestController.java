package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.dto.GenreRequest;
import com.tapcomiccomicreader.entity.Genre;
import com.tapcomiccomicreader.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreRestController {
    private final GenreService genreService;

    @Autowired
    public GenreRestController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.findAll();
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<Genre> getGenreById(@PathVariable int genreId) {
        return ResponseEntity.ok(genreService.findById(genreId));
    }

    @PostMapping
    public ResponseEntity<String> createGenre(@Valid @RequestBody GenreRequest request) {
        genreService.save(request.name());

        return ResponseEntity.ok("created genre - " + request.name());
    }

    @PutMapping("/{genreId}")
    public ResponseEntity<String> updateGenre(@PathVariable int genreId,
                                              @Valid @RequestBody GenreRequest request) {
        genreService.updateGenreName(genreId, request.name());

        return ResponseEntity.ok("updated genre id - " + genreId + " name to - " + request.name());
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<String> deleteGenre(@PathVariable int genreId) {
        genreService.deleteGenre(genreId);

        return ResponseEntity.ok("deleted genre id - " + genreId);
    }
}
