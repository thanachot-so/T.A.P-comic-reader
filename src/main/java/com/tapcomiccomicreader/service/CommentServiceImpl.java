package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.CommentRepository;
import com.tapcomiccomicreader.dto.CommentRequest;
import com.tapcomiccomicreader.dto.ReplyRequest;
import com.tapcomiccomicreader.entity.*;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final ReplyCommentService replyService;
    private final UserService userService;
    private final ComicService comicService;
    private final ChapterService chapterService;
    private final PageService pageService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ReplyCommentService replyService, UserService userService, ComicService comicService, ChapterService chapterService, PageService pageService) {
        this.commentRepository = commentRepository;
        this.replyService = replyService;
        this.userService = userService;
        this.comicService = comicService;
        this.chapterService = chapterService;
        this.pageService = pageService;
    }

    @Override
    public Comment find(int id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("could not find comment with id - " + id));
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public void addComment(CommentRequest commentRequest) {
        User user = userService.findByUuid(commentRequest.getUserUuid());
        String text = commentRequest.getText();

        Comment comment = new Comment(user, text);

        if (commentRequest.isPageComment()) {
            Page page = pageService.find(commentRequest.getPageId());
            comment.setPage(page);
        } else if (commentRequest.isChapterComment()) {
            Chapter chapter = chapterService.find(commentRequest.getChapterId());
            comment.setChapter(chapter);
        } else {
            Comic comic = comicService.find(commentRequest.getComicId());
            comment.setComic(comic);
        }

        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findByComicId(int comicId) {
        return commentRepository.findByComicId(comicId);
    }

    @Override
    public List<Comment> findByChapterId(int chapterId) {
        return commentRepository.findByChapterId(chapterId);
    }

    @Override
    public List<Comment> findByPageId(int pageId) {
        return commentRepository.findByPageId(pageId);
    }

    @Override
    @Transactional
    public void reply(ReplyRequest replyRequest) {
        User user = userService.findByUuid(replyRequest.getUuid());
        Comment mainComment = find(replyRequest.getCommentId());

        var reply = new ReplyComment();
        reply.setUser(user);
        reply.setText(replyRequest.getText());
        reply.setMainComment(mainComment);

        replyService.save(reply);
    }

    @Override
    @Transactional
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}

