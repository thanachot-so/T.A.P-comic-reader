package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dto.CommentRequest;
import com.tapcomiccomicreader.dto.ReplyRequest;
import com.tapcomiccomicreader.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment find(int id);

    List<Comment> findAll();

    void addComment(CommentRequest commentRequest);

    List<Comment> findByComicId(int comicId);

    List<Comment> findByChapterId(int chapterId);

    List<Comment> findByPageId(int pageId);

    void reply(ReplyRequest replyRequest);

    void delete(Comment comment);
}
