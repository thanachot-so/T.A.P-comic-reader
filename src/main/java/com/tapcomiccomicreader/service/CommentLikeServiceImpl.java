package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.CommentLikeRepository;
import com.tapcomiccomicreader.dao.CommentRepository;
import com.tapcomiccomicreader.dao.UserRepository;
import com.tapcomiccomicreader.entity.Comment;
import com.tapcomiccomicreader.entity.CommentLike;
import com.tapcomiccomicreader.entity.User;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeServiceImpl extends AbstractLikeableService<Comment, CommentLike> implements CommentLikeService{
    private final CommentRepository commentRepository;


    protected CommentLikeServiceImpl(UserRepository userRepository, CommentLikeRepository commentLikeRepository, CommentRepository commentRepository) {
        super(commentLikeRepository , userRepository);
        this.commentRepository = commentRepository;
    }

    @Override
    protected CommentLike createNewLikeable(User user, Comment comment, boolean vote) {
        return new CommentLike(comment, user, vote);
    }

    @Override
    protected Comment getTarget(int targetId) {
        return commentRepository.findById(targetId).orElseThrow(() -> new ResourceNotFoundException(""));
    }
}
