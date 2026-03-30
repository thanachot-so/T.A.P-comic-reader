package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.ChapterLikeRepository;
import com.tapcomiccomicreader.dao.ChapterRepository;
import com.tapcomiccomicreader.dao.UserRepository;
import com.tapcomiccomicreader.entity.Chapter;
import com.tapcomiccomicreader.entity.ChapterLike;
import com.tapcomiccomicreader.entity.User;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChapterLikeServiceImpl implements ChapterLikeService{
    private final ChapterLikeRepository chapterLikeRepository;
    private final ChapterRepository chapterRepository;
    private final UserRepository userRepository;


    @Autowired
    public ChapterLikeServiceImpl(ChapterLikeRepository chapterLikeRepository, ChapterRepository chapterRepository, UserRepository userRepository) {
        this.chapterLikeRepository = chapterLikeRepository;
        this.chapterRepository = chapterRepository;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public void save(ChapterLike chapterLike) {
        chapterLikeRepository.save(chapterLike);
    }

    @Override
    @Transactional
    public void vote(int userId, int chapterId, boolean vote) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("could not find user with id " + userId));

        var chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new ResourceNotFoundException("could not find chapter with id " + userId));

        chapterLikeRepository.findByUserIdAndChapterId(userId, chapterId)
                .ifPresentOrElse(
        existingVote -> handleExistingVote(existingVote, chapter, vote),
                    () -> handleNewVote(user, chapter, vote)
                );
    }

    private void handleExistingVote(ChapterLike existingVote, Chapter chapter, boolean isLike) {
        if (existingVote.isLiked() == isLike) {
            chapterLikeRepository.delete(existingVote);
            handleAdjustChapterVote(chapter, isLike, -1);
        } else {
            existingVote.setLiked(isLike);
            handleAdjustChapterVote(chapter, !isLike, -1);
            handleAdjustChapterVote(chapter, isLike, 1);
        }
    }

    private void handleNewVote(User user, Chapter chapter, boolean vote) {
        var chapterLike = new ChapterLike(user, chapter, vote);

        chapterLikeRepository.save(chapterLike);
        handleAdjustChapterVote(chapter, vote, 1);
    }

    private void handleAdjustChapterVote(Chapter chapter, boolean isLike, int amount) {
        if (isLike) {
            chapter.setLikeCount(chapter.getLikeCount() + amount);
        } else {
            chapter.setDislikeCount(chapter.getDislikeCount() + amount);
        }
    }
}
