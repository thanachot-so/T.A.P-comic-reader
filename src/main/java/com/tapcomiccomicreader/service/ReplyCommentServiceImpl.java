package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.ReplyCommentRepository;
import com.tapcomiccomicreader.entity.ReplyComment;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplyCommentServiceImpl implements ReplyCommentService{
    private final ReplyCommentRepository replyCommentRepository;

    public ReplyCommentServiceImpl(ReplyCommentRepository replyCommentRepository) {
        this.replyCommentRepository = replyCommentRepository;
    }

    @Override
    public ReplyComment find(int id) {
        return replyCommentRepository.findById(id)
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
