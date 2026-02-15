package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.entity.ReplyComment;

public interface ReplyCommentService {
    ReplyComment find(int id);

    void save(ReplyComment reply);

    void delete(ReplyComment reply);
}
