package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.helperclass.SecurityUtils;
import com.tapcomiccomicreader.service.ChapterLikeService;
import com.tapcomiccomicreader.service.CommentLikeService;
import com.tapcomiccomicreader.service.ReplyLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vote")
public class VoteRestController {
    private final ChapterLikeService chapterLikeService;
    private final CommentLikeService commentLikeService;
    private final ReplyLikeService replyLikeService;

    @Autowired
    public VoteRestController(ChapterLikeService chapterLikeService, CommentLikeService commentLikeService, ReplyLikeService replyLikeService) {
        this.chapterLikeService = chapterLikeService;
        this.commentLikeService = commentLikeService;
        this.replyLikeService = replyLikeService;
    }

    @PostMapping("/chapter/{chapterId}")
    public ResponseEntity<Void> voteChapter(
            @PathVariable int chapterId,
            @RequestParam boolean vote) {
        Integer userId = SecurityUtils.getCurrentUserId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        chapterLikeService.vote(userId, chapterId, vote);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<Void> voteComment(
            @PathVariable int commentId,
            @RequestParam boolean vote) {
        Integer userId = SecurityUtils.getCurrentUserId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        commentLikeService.vote(userId, commentId, vote);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reply/{replyId}")
    public ResponseEntity<Void> voteReply(
            @PathVariable int replyId,
            @RequestParam boolean vote) {
        Integer userId = SecurityUtils.getCurrentUserId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        replyLikeService.vote(userId, replyId, vote);
        return ResponseEntity.ok().build();
    }
}
