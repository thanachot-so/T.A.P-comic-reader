package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.ReplyCommentRepository;
import com.tapcomiccomicreader.dao.ReplyLikeRepository;
import com.tapcomiccomicreader.dto.ReplyCommentDTO;
import com.tapcomiccomicreader.entity.ReplyComment;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import com.tapcomiccomicreader.helperclass.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplyCommentServiceImpl implements ReplyCommentService{
    private final ReplyCommentRepository replyCommentRepository;
    private final ReplyLikeRepository replyLikeRepository;

    public ReplyCommentServiceImpl(ReplyCommentRepository replyCommentRepository, ReplyLikeRepository replyLikeRepository) {
        this.replyCommentRepository = replyCommentRepository;
        this.replyLikeRepository = replyLikeRepository;
    }

    @Override
    public ReplyCommentDTO find(int id) {
        Integer currentUserVote = SecurityUtils.getCurrentUserId();
        var reply = replyCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("could not find the reply with id -" + id));

        if (currentUserVote == null) {
            new ReplyCommentDTO(reply, null);
        }

        var replyLike = replyLikeRepository.findByUserIdAndTargetId(currentUserVote, id)
                .orElseThrow(() -> new ResourceNotFoundException(""));

        return new ReplyCommentDTO(reply, replyLike.isLiked());
    }

    @Override
    public ReplyComment findById(int id) {
        return  replyCommentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("could not find the reply with id -" + id));
    }

    @Override
    @Transactional
    public void save(ReplyComment reply) {
        replyCommentRepository.save(reply);
    }

    @Override
    @Transactional
    public void delete(ReplyComment reply) {
        replyCommentRepository.delete(reply);
    }
}
