package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.entity.ChapterLike;

import java.util.List;
import java.util.Map;

public interface ChapterLikeService {
    void save(ChapterLike chapterLike);
    void vote(int userId, int chapterId, boolean vote);
    Map<Integer, Boolean> getUserVotes(int userId, List<Integer> chapterIds);
}
