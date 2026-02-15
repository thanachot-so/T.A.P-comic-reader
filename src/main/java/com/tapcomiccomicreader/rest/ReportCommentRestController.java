package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.dto.ReportRequest;
import com.tapcomiccomicreader.entity.ReportedComment;
import com.tapcomiccomicreader.helperclass.ReportStatus;
import com.tapcomiccomicreader.service.ReportedCommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportCommentRestController {
    private final ReportedCommentService reportService;

    @Autowired
    public ReportCommentRestController(ReportedCommentService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<String> report(@RequestBody @Valid ReportRequest request) {
        reportService.report(request);
        return ResponseEntity.ok("added user " + request.getUuid() + " report");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateReport(@PathVariable Integer id, @RequestParam ReportStatus status) {
        switch (status) {
            case RESOLVED -> reportService.approve(id);
            case REJECTED -> reportService.reject(id);
            default -> ResponseEntity.badRequest().body("this status is not allowed: " + status);
        }

        return ResponseEntity.ok("changed report id: " + id + " to " + status);
    }

    @GetMapping
    public List<ReportedComment> findReports(@RequestParam(required = false) ReportStatus status) {
        if (status != null) {
            return reportService.findByStatus(status);
        }
        return reportService.findAll();
    }
}
