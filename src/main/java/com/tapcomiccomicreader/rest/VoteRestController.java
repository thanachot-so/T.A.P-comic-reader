package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.helperclass.SecurityUtils;
import com.tapcomiccomicreader.service.ChapterLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vote")
public class VoteRestController {
    private final ChapterLikeService chapterLikeService;

    @Autowired
    public VoteRestController(ChapterLikeService chapterLikeService) {
        this.chapterLikeService = chapterLikeService;
    }

    @PostMapping("/chapter/{chapterId}")
    public ResponseEntity<Void> voteChapter(@PathVariable int chapterId,
                                      @RequestParam boolean vote) {
        Integer userId = SecurityUtils.getCurrentUserId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        chapterLikeService.vote(userId, chapterId, vote);

        return ResponseEntity.ok().build();
    }
}
