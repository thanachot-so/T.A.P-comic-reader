package com.tapcomiccomicreader.service;

import java.util.List;
import java.util.Map;

public interface CommentLikeService {
    void vote(int userId, int commentId, boolean vote);
    Map<Integer, Boolean> getUserVotes(int userId, List<Integer> commentIds);
}
