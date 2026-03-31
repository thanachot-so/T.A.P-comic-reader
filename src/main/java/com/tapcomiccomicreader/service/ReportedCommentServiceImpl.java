package com.tapcomiccomicreader.service;

import com.tapcomiccomicreader.dao.ReportedCommentRepository;
import com.tapcomiccomicreader.dto.ReportRequest;
import com.tapcomiccomicreader.entity.ReportedComment;
import com.tapcomiccomicreader.entity.User;
import com.tapcomiccomicreader.exception.ResourceNotFoundException;
import com.tapcomiccomicreader.helperclass.ReportStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportedCommentServiceImpl implements ReportedCommentService{
    private final ReportedCommentRepository reportRepository;
    private final UserService userService;
    private final ReplyCommentService replyService;
    private final CommentService commentService;

    @Autowired
    public ReportedCommentServiceImpl(ReportedCommentRepository reportRepository, ReplyCommentService replyService, UserService userService, CommentService commentService) {
        this.reportRepository = reportRepository;
        this.replyService = replyService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @Override
    @Transactional
    public void report(ReportRequest request) {
        User reporter = userService.findByUuid(request.getUuid());
        var report = new ReportedComment();
        report.setUser(reporter);
        report.setReason(request.getReason());

        if (request.commentType().equals("COMMENT")) {
            var comment = commentService.find(request.getCommentId());
            report.setComment(comment);
        } else if (request.commentType().equals("REPLY")) {
            var reply = replyService.findById(request.getReplyId());
            report.setReply(reply);
        } else {
            throw new RuntimeException("no report type");
        }

        reportRepository.save(report);
    }

    @Override
    public ReportedComment find(int id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("could not find report with id - " + id));
    }

    @Override
    @Transactional
    public void approve(int id) {
        var report = find(id);

        if (report.getStatus() == ReportStatus.RESOLVED) {
            return;
        }

        if (report.getComment() != null) {
            var commentToDelete = report.getComment();
            var allReports = reportRepository.findAllByComment(commentToDelete);

            for (ReportedComment r : allReports) {
                r.setComment(null);
                r.setStatus(ReportStatus.RESOLVED);
            }

            reportRepository.saveAllAndFlush(allReports);
            commentService.delete(commentToDelete);
        } else if (report.getReply() != null) {
            var replyToDelete = report.getReply();
            var allReports = reportRepository.findAllByReply(replyToDelete);

            for (ReportedComment r : allReports) {
                r.setReply(null);
                r.setStatus(ReportStatus.RESOLVED);
            }

            reportRepository.saveAllAndFlush(allReports);
            replyService.delete(replyToDelete);
        }
    }

    @Override
    @Transactional
    public void reject(int id) {
        var report = find(id);
        report.setStatus(ReportStatus.REJECTED);
        reportRepository.save(report);
    }

    @Override
    public List<ReportedComment> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public List<ReportedComment> findByStatus(ReportStatus status) {
        return reportRepository.findByStatus(status);
    }
}
