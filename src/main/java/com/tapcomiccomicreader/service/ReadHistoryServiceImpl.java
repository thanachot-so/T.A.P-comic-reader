package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.ReadHistoryRepository;
import com.tapcomiccomicreader.dto.ReadHistoryRequest;
import com.tapcomiccomicreader.entity.Chapter;
import com.tapcomiccomicreader.entity.Comic;
import com.tapcomiccomicreader.entity.ReadHistory;
import com.tapcomiccomicreader.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReadHistoryServiceImpl implements ReadHistoryService {
    private final ReadHistoryRepository readHistoryRepository;
    private final ChapterService chapterService;
    private final UserService userService;

    @Autowired
    public ReadHistoryServiceImpl(ReadHistoryRepository readHistoryRepository, ChapterService chapterService, UserService userService) {
        this.readHistoryRepository = readHistoryRepository;
        this.chapterService = chapterService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void record(ReadHistoryRequest request) {
        User user = userService.findByUuid(request.getUserUuid());
        Chapter chapter = chapterService.find(request.getChapterId());
        Comic comic = chapter.getComic();
        int pageNumber = request.getPageNumber();
        var requestHistory = readHistoryRepository.findByUserAndComic(comic, user);
        ReadHistory history;

        if (requestHistory.isPresent()) {
            history = requestHistory.get();
        } else {
            history = new ReadHistory();
            history.setUser(user);
            history.setComic(comic);
        }

        history.setReadChapter(chapter);
        history.setPageNumber(pageNumber);

        readHistoryRepository.save(history);
    }

    @Override
    public List<ReadHistory> findAll() {
        return readHistoryRepository.findAll();
    }

    @Override
    public List<ReadHistory> findByUuid(String uuid) {
        return readHistoryRepository.findByUuid(uuid);
    }
}
