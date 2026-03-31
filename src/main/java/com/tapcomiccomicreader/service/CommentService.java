package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dto.CommentDTO;
import com.tapcomiccomicreader.dto.CommentRequest;
import com.tapcomiccomicreader.dto.ReplyRequest;
import com.tapcomiccomicreader.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment find(int id);

    List<CommentDTO> findAll();

    void addComment(CommentRequest commentRequest);

    List<CommentDTO> findByComicId(int comicId);

    List<CommentDTO> findByChapterId(int chapterId);

    List<CommentDTO> findByPageId(int pageId);

    void reply(ReplyRequest replyRequest);

    void delete(Comment comment);
}
