package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.ReplyCommentRepository;
import com.tapcomiccomicreader.dao.ReplyLikeRepository;
import com.tapcomiccomicreader.dao.UserRepository;
import com.tapcomiccomicreader.entity.ReplyComment;
import com.tapcomiccomicreader.entity.ReplyLike;
import com.tapcomiccomicreader.entity.User;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyLikeServiceImpl extends AbstractLikeableService<ReplyComment, ReplyLike> implements ReplyLikeService {
    private final ReplyCommentRepository replyRepository;

    @Autowired
    protected ReplyLikeServiceImpl(ReplyLikeRepository replyLikeRepository, UserRepository userRepository, ReplyCommentRepository replyRepository) {
        super(replyLikeRepository, userRepository);
        this.replyRepository = replyRepository;
    }

    @Override
    protected ReplyLike createNewLikeable(User user, ReplyComment replyComment, boolean vote) {
        return new ReplyLike(user, replyComment, vote);
    }

    @Override
    protected ReplyComment getTarget(int targetId) {
        return replyRepository.findById(targetId).orElseThrow(() -> new ResourceNotFoundException(""));
    }
}
