package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.entity.ChapterLike;

public interface ChapterLikeService {
    void save(ChapterLike chapterLike);

    void vote(int userId, int chapterId, boolean vote);
}
