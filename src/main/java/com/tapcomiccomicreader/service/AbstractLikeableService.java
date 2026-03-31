package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.GenericLikeableRepository;
import com.tapcomiccomicreader.dao.UserRepository;
import com.tapcomiccomicreader.entity.User;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import com.tapcomiccomicreader.helperclass.Interactable;
import com.tapcomiccomicreader.helperclass.Likeable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractLikeableService<T_TARGET extends Interactable, T_LIKE extends Likeable> {
    private final GenericLikeableRepository<T_LIKE> likeableRepository;
    private final UserRepository userRepository;

    protected AbstractLikeableService(GenericLikeableRepository<T_LIKE> likeableRepository, UserRepository userRepository) {
        this.likeableRepository = likeableRepository;
        this.userRepository = userRepository;
    }

    protected abstract T_LIKE createNewLikeable(User user, T_TARGET target, boolean vote);
    protected abstract T_TARGET getTarget(int targetId);

    @Transactional
    public void vote(int userId, int targetId, boolean vote) {
        T_TARGET target = getTarget(targetId);

        likeableRepository.findByUserIdAndTargetId(userId, targetId)
                .ifPresentOrElse(
                        existingVote -> handleExistingVote(existingVote, target, vote),
                        () -> handleNewVote(userId, target, vote)
                );
    }

    public Map<Integer,Boolean> getUserVotes(int userId, List<Integer> targetIds) {
        if (targetIds == null || targetIds.isEmpty()) {
            return Collections.emptyMap();
        }

        var userVotes = likeableRepository.findByUserIdAndTargetIds(userId, targetIds);

        return userVotes.stream()
                .collect(Collectors.toMap(
                        Likeable::getTargetId,
                        Likeable::isLiked
                ));
    }

    private void handleExistingVote(T_LIKE existingVote, T_TARGET target, boolean isLike) {
        if (existingVote.isLiked() == isLike) {
            likeableRepository.delete(existingVote);
            handleAdjustChapterVote(target, isLike, -1);
        } else {
            existingVote.setLike(isLike);
            handleAdjustChapterVote(target, !isLike, -1);
            handleAdjustChapterVote(target, isLike, 1);
        }
    }

    private void handleNewVote(int userId, T_TARGET target, boolean vote) {
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException(""));
        T_LIKE like = createNewLikeable(user, target, vote);

        likeableRepository.save(like);
        handleAdjustChapterVote(target, vote, 1);
    }

    private void handleAdjustChapterVote(T_TARGET target, boolean isLike, int amount) {
        if (isLike) {
            target.setLikeCount(target.getLikeCount() + amount);
        } else {
            target.setDislikeCount(target.getDislikeCount() + amount);
        }
    }


}
