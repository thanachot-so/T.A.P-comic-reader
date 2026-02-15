package com.tapcomiccomicreader.rest;

import com.tapcomiccomicreader.dto.ReadHistoryRequest;
import com.tapcomiccomicreader.entity.ReadHistory;
import com.tapcomiccomicreader.service.ReadHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history/")
public class HistoryRestController {
    private final ReadHistoryService readHistoryService;

    @Autowired
    public HistoryRestController(ReadHistoryService readHistoryService) {
        this.readHistoryService = readHistoryService;
    }

    @GetMapping("")
    public List<ReadHistory> findAll() {
        return readHistoryService.findAll();
    }

    @GetMapping("/{uuid}")
    public List<ReadHistory> find(@PathVariable String uuid) {
        return readHistoryService.findByUuid(uuid);
    }

    @PostMapping("/record")
    public void record(@RequestBody @Valid ReadHistoryRequest request) {
        readHistoryService.record(request);
    }
}
