package com.tapcomiccomicreader.service;

import java.util.List;
import java.util.Map;

public interface ReplyLikeService {
    void vote(int userId, int replyId, boolean vote);
    Map<Integer, Boolean> getUserVotes(int userId, List<Integer> replyIds);
}
