package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dto.ReplyCommentDTO;
import com.tapcomiccomicreader.entity.ReplyComment;

public interface ReplyCommentService {
    ReplyCommentDTO find(int id);

    ReplyComment findById(int id);

    void save(ReplyComment reply);

    void delete(ReplyComment reply);
}
