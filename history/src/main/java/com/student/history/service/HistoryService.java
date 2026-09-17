package com.student.history.service;

import com.student.history.entity.History;
import com.student.history.repo.HistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }


    public History saveHistory(History history) {

        // Store the submission time
        history.setAttemptedAt(LocalDateTime.now());

        return historyRepository.save(history);
    }

    public List<History> getStudentHistory(Long studentId) {
        return historyRepository.findByStudentId(studentId);
    }
}