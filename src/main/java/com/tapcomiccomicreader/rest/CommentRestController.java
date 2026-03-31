package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.dto.CommentDTO;
import com.tapcomiccomicreader.dto.CommentRequest;
import com.tapcomiccomicreader.dto.ReplyRequest;
import com.tapcomiccomicreader.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {
    private final CommentService commentService;

    @Autowired
    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add/comment")
    public ResponseEntity<String> addComment(@RequestBody @Valid CommentRequest commentRequest) {
            commentService.addComment(commentRequest);
            return ResponseEntity.ok("user: " + commentRequest.getUserUuid() + " commented: " + commentRequest.getText());
    }

    @PostMapping("/add/reply")
    public ResponseEntity<String> addReply(@RequestBody @Valid ReplyRequest reply) {
        commentService.reply(reply);
        return ResponseEntity.ok("user: " + reply.getUuid() + " replied: " + reply.getText() + " to comment id: " + reply.getCommentId());
    }

    @GetMapping("/comic/{comicId}")
    public List<CommentDTO> getComicComments(@PathVariable int comicId) {
        return commentService.findByComicId(comicId);
    }

    @GetMapping("/chapter/{chapterId}")
    public List<CommentDTO> getChapterComments(@PathVariable int chapterId) {
        return commentService.findByChapterId(chapterId);
    }

    @GetMapping("/page/{pageId}")
    public List<CommentDTO> getPageComments(@PathVariable int pageId) {
        return commentService.findByPageId(pageId);
    }

}
