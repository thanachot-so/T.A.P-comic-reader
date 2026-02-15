package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dto.ReadHistoryRequest;
import com.tapcomiccomicreader.entity.ReadHistory;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReadHistoryService {
    void record(ReadHistoryRequest request);

    List<ReadHistory> findAll();

    List<ReadHistory> findByUuid(@Param("uuid") String uuid);
}
