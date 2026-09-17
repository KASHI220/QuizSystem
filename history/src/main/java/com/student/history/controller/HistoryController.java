package com.student.history.controller;

import com.student.history.entity.History;
import com.student.history.service.HistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @PostMapping
    public History saveHistory(@RequestBody History history) {
        return historyService.saveHistory(history);
    }

    @GetMapping("/student/{studentId}")
    public List<History> getStudentHistory(
            @PathVariable Long studentId) {

        return historyService.getStudentHistory(studentId);
    }
}