package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dto.ReportRequest;
import com.tapcomiccomicreader.entity.ReportedComment;
import com.tapcomiccomicreader.helperclass.ReportStatus;

import java.util.List;

public interface ReportedCommentService {
    void report(ReportRequest request);

    ReportedComment find(int id);

    void approve(int id);

    void reject(int id);

    List<ReportedComment> findAll();

    List<ReportedComment> findByStatus(ReportStatus status);
}
