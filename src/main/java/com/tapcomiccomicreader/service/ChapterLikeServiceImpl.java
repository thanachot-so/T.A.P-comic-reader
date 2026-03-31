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
public class ChapterLikeServiceImpl extends AbstractLikeableService<Chapter, ChapterLike> implements ChapterLikeService{
    private final ChapterLikeRepository chapterLikeRepository;
    private final ChapterRepository chapterRepository;


    @Autowired
    public ChapterLikeServiceImpl(ChapterLikeRepository chapterLikeRepository, UserRepository userRepository, ChapterRepository chapterRepository) {
        super(chapterLikeRepository, userRepository);
        this.chapterLikeRepository = chapterLikeRepository;
        this.chapterRepository = chapterRepository;
    }


    @Override
    @Transactional
    public void save(ChapterLike chapterLike) {
        chapterLikeRepository.save(chapterLike);
    }

    @Override
    protected ChapterLike createNewLikeable(User user, Chapter chapter, boolean vote) {
        return new ChapterLike(user, chapter, vote);
    }

    @Override
    protected Chapter getTarget(int targetId) {
        return chapterRepository.findById(targetId).orElseThrow(() -> new ResourceNotFoundException(""));
    }
}
